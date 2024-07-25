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


    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<Object> handleException(GlobalException e) {
        return buildResponseEntity(HttpStatus.valueOf(e.getStatusCode()), e.getMessage() , e.getStatusCode() + "");
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status ,  String message ,String code ) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", status.getReasonPhrase());
        errorDetails.put("message", message);
        errorDetails.put("errorCode", code);
        return new ResponseEntity<>(errorDetails, status);
    }
    private ResponseEntity<Object> buildResponseEntity(HttpStatus status ,  String message  ) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", status.getReasonPhrase());
        errorDetails.put("message", message);
        return new ResponseEntity<>(errorDetails, status);
    }

}
