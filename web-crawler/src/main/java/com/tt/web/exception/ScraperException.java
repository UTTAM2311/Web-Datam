package com.tt.web.exception;

import com.tt.core.exception.AbstractException;

public class ScraperException extends AbstractException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ScraperException(String message) {
        super(message);
    }

    public ScraperException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
