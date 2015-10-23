package com.leonoss.wechat.apppay.exception;

public class WechatAppPayServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7723215745332446916L;

	public WechatAppPayServiceException() {
		super();
	}

	public WechatAppPayServiceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WechatAppPayServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public WechatAppPayServiceException(String message) {
		super(message);
	}

	public WechatAppPayServiceException(Throwable cause) {
		super(cause);
	}
}
