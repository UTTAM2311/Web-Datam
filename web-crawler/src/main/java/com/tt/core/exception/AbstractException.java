package com.tt.core.exception;

public abstract class AbstractException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    

    /* --- Constructors --- */
    
    public AbstractException(String message) {
        super(message);
    }

    public AbstractException(String message, Throwable throwable) {
        super(message, throwable);
    }


}
