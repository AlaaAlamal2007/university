package com.example.alaa.university.controller.exceptions;

import com.example.alaa.university.exceptions.ArgumentSubjectException;
import com.example.alaa.university.exceptions.ResourceSubjectIsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SubjectExceptionHandler {
    @ResponseBody
    @ExceptionHandler({ResourceSubjectIsNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleResourceNotFoundException(
            ResourceSubjectIsNotFoundException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({ArgumentSubjectException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleArgumentAddressException(
            ArgumentSubjectException exception) {
        return new ErrorMessage(exception.getMessage());
    }
}


