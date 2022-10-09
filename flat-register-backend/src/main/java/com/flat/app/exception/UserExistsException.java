package com.flat.app.exception;

@SuppressWarnings("serial")
public class UserExistsException extends Exception {

	public UserExistsException(String message) {
		super(message);
	}

}
