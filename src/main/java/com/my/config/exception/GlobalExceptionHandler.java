package com.my.config.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Object> handleJwtTokenExpiredException(JwtException e) {
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String message) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", status.getReasonPhrase());
        errorDetails.put("message", message);
        return new ResponseEntity<>(errorDetails, status);
    }

}
