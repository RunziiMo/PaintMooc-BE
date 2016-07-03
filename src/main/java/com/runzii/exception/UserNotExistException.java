package com.runzii.exception;


/**
 * Created by runzii on 16-4-19.
 */
public class UserNotExistException extends RuntimeException {

    public UserNotExistException() {
    }

    public UserNotExistException(String msg) {
        this.message = msg;
    }

    private String message;

    @Override
    public String getMessage() {
        return message;
    }
}
