package com.example.alaa.university.controller.exceptions;

import com.example.alaa.university.exceptions.ArgumentStudentException;
import com.example.alaa.university.exceptions.ResourceStudentIsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class StudentExceptionHandler {
    @ResponseBody
    @ExceptionHandler({ResourceStudentIsNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleResourceStudentIsNotFoundException(
            ResourceStudentIsNotFoundException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({ArgumentStudentException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleArgumentStudentException(
            ArgumentStudentException exception) {
        return new ErrorMessage(exception.getMessage());
    }
}


