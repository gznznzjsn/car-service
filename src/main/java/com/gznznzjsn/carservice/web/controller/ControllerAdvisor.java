package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.exception.IllegalActionException;
import com.gznznzjsn.carservice.domain.exception.NotEnoughResourcesException;
import com.gznznzjsn.carservice.domain.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFound(
            ResourceNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(NotEnoughResourcesException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String handleNotEnoughResources(
            ResourceNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(IllegalActionException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String handleIllegalAction(
            IllegalActionException e) {
        return e.getMessage();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {

        return e.getBindingResult()
                .getFieldErrors().stream()
                .collect(Collectors.toMap(
                                (FieldError::getField),
                                (fieldError ->
                                        fieldError.getDefaultMessage() == null ? "No message" : fieldError.getDefaultMessage()
                                )
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleOther(
            Exception e) {
        return e.getMessage();
    }
}
