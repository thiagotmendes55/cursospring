package com.mendes.cursospring.services.exception;

public class FileException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public FileException(String msg) {
		super(msg);		
	}
	
	public FileException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
