package com.education.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.text.ParseException;

@ControllerAdvice("com.education")
public class GlobalExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<AppError> handle403(RuntimeException e, WebRequest webRequest) {
        return new ResponseEntity<>(
                new AppError("{invalid.date}", webRequest, HttpStatus.FORBIDDEN),
                HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler({ParseException.class})
    public ResponseEntity<AppError> handle400(ParseException e, WebRequest webRequest) {
        return new ResponseEntity<>(
                new AppError("{invalid.date}", webRequest, HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }

}
