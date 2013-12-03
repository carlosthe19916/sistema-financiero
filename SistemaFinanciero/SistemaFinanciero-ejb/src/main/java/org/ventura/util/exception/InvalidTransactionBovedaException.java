package org.ventura.util.exception;

public class InvalidTransactionBovedaException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidTransactionBovedaException(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidTransactionBovedaException(String message) {
        super(message);
    }
}
