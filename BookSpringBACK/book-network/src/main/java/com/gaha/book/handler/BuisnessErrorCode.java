package com.gaha.book.handler;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public enum BuisnessErrorCode {
	NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "No Code"),
	INCORRECT_CURRENT_PASSWORD(300, HttpStatus.BAD_REQUEST, "Current password is incorrect"),
	NEW_PASSWORD_DOES_NOT_MATCH(301, HttpStatus.BAD_REQUEST, "The new password does not match"),

	ACCOUNT_LOCKED(302, HttpStatus.FORBIDDEN, "User Account is Locked"),
	ACCOUNT_DISABLED(302, HttpStatus.FORBIDDEN, "User Account is Locked"),
	ACCOUNT_BAD_CREDENTIALS(304, HttpStatus.FORBIDDEN, "lOGIN/PASSWORD is incorrect");

	;

	@Getter
	private final Integer code;
	@Getter
	private final String description;
	@Getter
	private final HttpStatus httpStatus;

	private BuisnessErrorCode(Integer code, HttpStatus httpStatus, String description) {
		this.code = code;
		this.description = description;
		this.httpStatus = httpStatus;
	}

}
