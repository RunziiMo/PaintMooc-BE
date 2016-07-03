package com.runzii.exception;

/**
 * Created by runzii on 16-4-20.
 */
public class VarifyErrorException extends RuntimeException {

    private String message;

    public VarifyErrorException() {
    }

    public VarifyErrorException(String msg) {
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
