package com.runzii.exception;

import com.runzii.model.entity.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by runzii on 16-4-20.
 */
@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(ParamErrorException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Error param(RuntimeException e) {
        logger.error(e.getMessage());
        return new Error("fuck", e.getMessage());
    }

    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Error userExist(RuntimeException e) {
        logger.error(e.getMessage());
        return new Error("fuck", e.getMessage());
    }

    @ExceptionHandler(UserNotExistException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Error UserNotExist(RuntimeException e) {
        logger.error(e.getMessage());
        return new Error("fuck", e.getMessage());
    }

    @ExceptionHandler(VarifyErrorException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Error varifyErr(RuntimeException e) {
        logger.error(e.getMessage());
        return new Error("fuck", e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public Error runtimeErr(RuntimeException e) {
        logger.error(e.getMessage());
        return new Error("fuck", e.getMessage());
    }
}
