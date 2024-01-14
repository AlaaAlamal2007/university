package com.example.alaa.university.controller.exceptions;

import com.example.alaa.university.exceptions.ArgumentTeacherException;
import com.example.alaa.university.exceptions.ResourceTeacherIsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TeacherExceptionHandler {
    @ResponseBody
    @ExceptionHandler({ResourceTeacherIsNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleResourceNotFoundException(
            ResourceTeacherIsNotFoundException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({ArgumentTeacherException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleArgumentAddressException(
            ArgumentTeacherException exception) {
        return new ErrorMessage(exception.getMessage());
    }
}


