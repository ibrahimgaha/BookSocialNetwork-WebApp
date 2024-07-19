package com.gaha.book.entities.exception;

public class OperationNotPermittedException extends RuntimeException {

	public OperationNotPermittedException(String msg) {
		super(msg);
	}
}
