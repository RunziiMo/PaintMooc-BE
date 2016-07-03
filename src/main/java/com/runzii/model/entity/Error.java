package com.runzii.model.entity;

/**
 * Created by runzii on 16-4-20.
 */
public class Error {

    private String error;
    private String error_description;

    public Error(String error, String error_description) {
        this.error = error;
        this.error_description = error_description;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }
}
