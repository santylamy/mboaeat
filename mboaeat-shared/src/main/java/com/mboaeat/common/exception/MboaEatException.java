package com.mboaeat.common.exception;

public class MboaEatException extends RuntimeException {

    protected String code;

    public MboaEatException() {
    }

    public MboaEatException(String message) {
        super(message);
    }

    public MboaEatException(String message, String code) {
        super(message);
        this.code = code;
    }

    public MboaEatException(String message, Throwable cause, String code) {
        this(message, cause);
        this.code = code;
    }

    public MboaEatException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCode() {
        return code;
    }
}
