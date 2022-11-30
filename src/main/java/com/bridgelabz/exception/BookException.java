package com.bridgelabz.exception;

public class BookException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BookException(String message) {
		this.message = message;
	}

	public BookException() {
		
	}
	
}
