package com.campus1.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ManagerNotFoundException.class)
    public ResponseEntity<Map<String, Object>> ManagerNotFound(ManagerNotFoundException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", e.getMessage()); // Use the message, not the exception object
        map.put("statusCode", HttpStatus.NOT_FOUND.value()); // Use 404, not 403
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(UsersNotFound.class)
    public ResponseEntity<Map<String, Object>> UsersNotFound(UsersNotFound e) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", e.getMessage()); // Use the message, not the exception object
        map.put("statusCode", HttpStatus.NOT_FOUND.value()); // Use 404, not 403
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }
}