package com.gaha.book.email;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private SpringTemplateEngine templateEngine;

	@Async
	public void SendEmail(String to, String userName, EmailTemplateName emailTemplate, String confirmationUrl,
			String activationCode, String subject) throws MessagingException {

		String templateName = emailTemplate.getName(); // Get the correct template name

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED,
				StandardCharsets.UTF_8.name());

		Map<String, Object> properties = new HashMap<>();
		properties.put("userName", userName);
		properties.put("confirmationUrl", confirmationUrl);
		properties.put("activationCode", activationCode);

		Context context = new Context();
		context.setVariables(properties);

		helper.setFrom("ibrahim.gaha@outlook.com");
		helper.setTo(to);
		helper.setSubject(subject);

		String template = templateEngine.process(templateName, context);
		helper.setText(template, true);

		mailSender.send(mimeMessage);
	}
}
