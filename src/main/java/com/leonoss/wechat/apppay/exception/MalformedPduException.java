package com.leonoss.wechat.apppay.exception;

public class MalformedPduException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7723215745332446916L;

	public MalformedPduException() {
		super();
	}

	public MalformedPduException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MalformedPduException(String message, Throwable cause) {
		super(message, cause);
	}

	public MalformedPduException(String message) {
		super(message);
	}

	public MalformedPduException(Throwable cause) {
		super(cause);
	}
}
