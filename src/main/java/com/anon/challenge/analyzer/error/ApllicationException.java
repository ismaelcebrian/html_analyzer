package com.anon.challenge.analyzer.error;

@SuppressWarnings("serial")
public class ApllicationException extends RuntimeException {

	public ApllicationException() {
		super();
	}

	public ApllicationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ApllicationException(String msg) {
		super(msg);
	}

	public ApllicationException(Throwable cause) {
		super(cause);
	}

}
