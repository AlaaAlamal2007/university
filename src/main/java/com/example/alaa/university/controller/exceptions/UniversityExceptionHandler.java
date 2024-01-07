package com.example.alaa.university.controller.exceptions;

import com.example.alaa.university.exceptions.ArgumentUniversityException;
import com.example.alaa.university.exceptions.ResourceUniversityIsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UniversityExceptionHandler {
    @ResponseBody
    @ExceptionHandler({ResourceUniversityIsNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleResourceUniversityIsNotFoundException(
            ResourceUniversityIsNotFoundException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({ArgumentUniversityException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleArgumentUniversityException(
            ArgumentUniversityException exception) {
        return new ErrorMessage(exception.getMessage());
    }
}


