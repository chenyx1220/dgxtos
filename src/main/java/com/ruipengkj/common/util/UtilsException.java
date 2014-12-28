package com.ruipengkj.common.util;

public class UtilsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UtilsException(String msg) {
		super(msg);
	}
	
	public UtilsException(String msg,Throwable obj) {
		super(msg, obj);
	}
}