package com.gaha.book.handler;

import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {

	private Integer buisnessErrorCode;
	private String buisnessErrorDescription;
	private String error;
	private Set<String> validationErrors;
	private Map<String, String> errors;

}
