package com.wf.apiwf.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler
    public ResponseEntity<String> processarErrorNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body((ex.getMessage()));
    }

    @ExceptionHandler
    public  ResponseEntity<String> processLoginError(UserLoginException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> JwtIncorrect(JwtIncorrectException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
