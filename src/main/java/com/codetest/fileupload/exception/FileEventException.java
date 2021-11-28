package com.codetest.fileupload.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;

@ControllerAdvice
@Slf4j
public class FileEventException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExceptions(Exception exception, WebRequest webRequest) {
        log.error(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return entity;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleExceptions(IllegalArgumentException exception, WebRequest webRequest) {
        log.error(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return entity;
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Object> handleExceptions(FileNotFoundException exception, WebRequest webRequest) {
        log.error(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return entity;
    }
}
