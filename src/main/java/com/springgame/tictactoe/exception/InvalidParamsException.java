package com.springgame.exception;

public class InvalidParamsException extends Exception {
    private String message;

    public InvalidParamsException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
