package org.ventura.util.exception;

public class InsufficientMoneyForTransactionException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InsufficientMoneyForTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
    public InsufficientMoneyForTransactionException(String message) {
        super(message);
    }
}
