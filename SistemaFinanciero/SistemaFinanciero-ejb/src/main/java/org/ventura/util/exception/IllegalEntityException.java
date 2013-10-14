package org.ventura.util.exception;

public class IllegalEntityException extends Exception {

private static final long serialVersionUID = 1L;
	
	public IllegalEntityException(String message, Throwable cause) {
        super(message, cause);
    }
	
    public IllegalEntityException(String message) {
        super(message);
    }
}
