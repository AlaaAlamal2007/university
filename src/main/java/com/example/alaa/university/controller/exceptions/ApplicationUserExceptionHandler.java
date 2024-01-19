package com.example.alaa.university.controller.exceptions;

import com.example.alaa.university.exceptions.ResourceApplicationUserIsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApplicationUserExceptionHandler {
    @ResponseBody
    @ExceptionHandler({ResourceApplicationUserIsNotFoundException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleResourceNotFoundException(
            ResourceApplicationUserIsNotFoundException exception) {
        return new ErrorMessage(exception.getMessage());
    }
}


