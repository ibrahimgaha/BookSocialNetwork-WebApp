package com.gaha.book.entities.auth;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gaha.book.email.EmailService;
import com.gaha.book.email.EmailTemplateName;
import com.gaha.book.entities.Token;
import com.gaha.book.entities.User;
import com.gaha.book.repositories.RoleRepository;
import com.gaha.book.repositories.TokenRepository;
import com.gaha.book.repositories.UserRepository;
import com.gaha.book.services.JwtService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenRepository tokenRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	private final EmailService emailService;

	@Value("${application.security.mailing.frontend.activation-url}")
	private String activationToken;

	public void register(RegistrationRequest request) throws MessagingException {
		var userRole = roleRepository.findByName("USER")
				.orElseThrow(() -> new IllegalStateException("ROLE USER was not initiated"));

		var user = User.builder().firstname(request.getFirstname()).lastname(request.getLastname())
				.email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).accountLocked(false)
				.enabled(false).roles(List.of(userRole)).build();

		userRepository.save(user);
		sendValidationEmail(user);
	}

	private void sendValidationEmail(User user) throws MessagingException {
		var newToken = generateAndSaveActivationToken(user);
		emailService.SendEmail(user.getEmail(), user.fullName(), EmailTemplateName.ACTIVATE_ACCOUNT, activationToken,
				newToken, "Account activation");
	}

	private String generateAndSaveActivationToken(User user) {
		String generatedToken = generateActivationToken(6);
		var token = Token.builder().token(generatedToken).createdAt(LocalDateTime.now())
				.expiresAt(LocalDateTime.now().plusMinutes(15)).user(user).build();
		tokenRepository.save(token);
		return generatedToken;
	}

	private String generateActivationToken(int length) {
		String characters = "0123456789";
		StringBuilder codeBuilder = new StringBuilder();
		SecureRandom secureRandom = new SecureRandom();
		for (int i = 0; i < length; i++) {
			int randomIndex = secureRandom.nextInt(characters.length());
			codeBuilder.append(characters.charAt(randomIndex));
		}
		return codeBuilder.toString();
	}

	public AuthenticationResponse authenticate(AuthenticationrRquest request) {
		var auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var claims = new HashMap<String, Object>();
		var user = ((User) auth.getPrincipal());
		claims.put("fullName", user.fullName());
		var jwtToken = jwtService.generateToken(claims, user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	public void activateAccount(String token) throws MessagingException {
		Token savedToken = tokenRepository.findByToken(token)
				.orElseThrow(() -> new RuntimeException("Invalid Token !!"));
		if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
			sendValidationEmail(savedToken.getUser());
			throw new RuntimeException(
					"Activation Token Has Expired !! A new one will be sent to the same email adress");
		}

		var user = userRepository.findById(savedToken.getUser().getId())
				.orElseThrow(() -> new UsernameNotFoundException("User not found !!"));
		user.setEnabled(true);
		userRepository.save(user);
		savedToken.setValidatedAt(LocalDateTime.now());
		tokenRepository.save(savedToken);
	}
}
