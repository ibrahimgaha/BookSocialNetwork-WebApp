package com.gaha.book.entities.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {

	@NotEmpty(message = "FirstName is mandatory")
	private String firstname;

	@NotEmpty(message = "LastName is mandatory")
	private String lastname;

	@NotEmpty(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	private String email;

	@NotEmpty(message = "Password is mandatory")
	@Size(min = 8, message = "Password should be 8 characters long minimum")
	private String password;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

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
