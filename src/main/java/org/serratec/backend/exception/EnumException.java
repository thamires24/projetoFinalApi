package org.serratec.backend.exception;

public class EnumException extends RuntimeException { 

    private static final long serialVersionUID = 1L;

    public EnumException(String message) {
        super(message);
    }

    public EnumException(String message, Throwable cause) {
        super(message, cause);
    }
}