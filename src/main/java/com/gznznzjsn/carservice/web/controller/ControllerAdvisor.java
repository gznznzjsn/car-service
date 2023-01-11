package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.exception.NotEnoughResources;
import com.gznznzjsn.carservice.domain.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
//    @ExceptionHandler(ResourceNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String handleResourceNotFoundException(
//            ResourceNotFoundException e) {
//        return e.getMessage();
//    }
//
//    @ExceptionHandler(NotEnoughResources.class)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public String handleCityNotEnoughResourcesException(
//            ResourceNotFoundException e) {
//        return e.getMessage();
//    }
}
