package com.gaha.book.entities.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public class AuthenticationrRquest {

	@NotEmpty(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	private String email;

	@NotEmpty(message = "Password is mandatory")
	@Size(min = 8, message = "Password should be 8 characters long minimum")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
