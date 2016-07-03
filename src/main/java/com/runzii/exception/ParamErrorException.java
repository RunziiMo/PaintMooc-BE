package com.runzii.exception;

/**
 * Created by runzii on 16-4-20.
 */
public class ParamErrorException extends RuntimeException {



    public ParamErrorException() {
    }

    public ParamErrorException(String msg) {
        this.message = msg;
    }

    private String message;

    @Override
    public String getMessage() {
        return message;
    }
}
