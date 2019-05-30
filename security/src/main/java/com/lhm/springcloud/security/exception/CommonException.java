/**
 * 
 */
package com.lhm.springcloud.security.exception;

public class CommonException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int exceptionCode;
	private String message;

	/**
	 * @param exceptionCode
	 */
	public CommonException(int exceptionCode, String message) {
		super(message);
		this.exceptionCode = exceptionCode;
		this.message = message;
	}

	/**
	 * @return the exceptionCode
	 */
	public int getExceptionCode() {
		return exceptionCode;
	}

	/**
	 * @param exceptionCode
	 *            the exceptionCode to set
	 */
	public void setExceptionCode(int exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	@Override
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
