package com.terpel.poc.wsterpeleds.model;

public class CustomException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CustomException() {
		super();
	}

	public CustomException(String s) {
		super(s);
	}

	public CustomException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public CustomException(Throwable throwable) {
		super(throwable);
	}
}
