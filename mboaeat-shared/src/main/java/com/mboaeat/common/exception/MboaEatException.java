package com.mboaeat.common.exception;

public class MboaEatException extends RuntimeException {

    protected String errorCode;

    public MboaEatException() {
    }

    public MboaEatException(String message) {
        super(message);
    }

    public MboaEatException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public MboaEatException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
