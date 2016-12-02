package com.tt.web.exception;

import com.tt.core.exception.AbstractException;

public class CrawlerException extends AbstractException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public CrawlerException(String message) {
        super(message);
    }

    public CrawlerException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
