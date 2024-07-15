package com.gaha.book.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaha.book.entities.auth.AuthenticationResponse;
import com.gaha.book.entities.auth.AuthenticationService;
import com.gaha.book.entities.auth.AuthenticationrRquest;
import com.gaha.book.entities.auth.RegistrationRequest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
@Slf4j
public class AuthenticationController {

	private final AuthenticationService authService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest request) throws MessagingException {
		authService.register(request);
		return ResponseEntity.accepted().build();
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticationResponse(
			@Valid @RequestBody AuthenticationrRquest request) throws MessagingException {
		return ResponseEntity.ok(authService.authenticate(request));
	}

	@GetMapping("/activate-account")
	public void confirm(@RequestParam String token) throws MessagingException {
		authService.activateAccount(token);
	}

}
