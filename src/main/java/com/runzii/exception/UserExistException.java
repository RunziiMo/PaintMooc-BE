package com.runzii.exception;


/**
 * Created by runzii on 16-4-19.
 */
public class UserExistException extends RuntimeException {

    public UserExistException() {
    }

    public UserExistException(String msg) {
        this.message = msg;
    }

    private String message;

    @Override
    public String getMessage() {
        return message;
    }
}
