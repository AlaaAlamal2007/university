package com.example.alaa.university.controller.exceptions;

import com.example.alaa.university.exceptions.ArgumentAddressException;
import com.example.alaa.university.exceptions.ResourceAddressIsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AddressExceptionHandler {
    @ResponseBody
    @ExceptionHandler({ResourceAddressIsNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleResourceNotFoundException(
            ResourceAddressIsNotFoundException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({ArgumentAddressException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleArgumentAddressException(
            ArgumentAddressException exception) {
        return new ErrorMessage(exception.getMessage());
    }
}


