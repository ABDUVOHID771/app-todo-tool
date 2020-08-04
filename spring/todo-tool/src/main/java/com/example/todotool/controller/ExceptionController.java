package com.example.todotool.controller;

import com.example.todotool.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<?> handleProjectNotFoundException(ResourceNotFoundException resourceNotFoundException, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(resourceNotFoundException.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<?> handleProjectDuplicatedException(DuplicateException duplicateException, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(duplicateException.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<?> handleUsernameDuplicatedException(UsernameException usernameAlreadyExists, WebRequest webRequest) {
        UsernameAlreadyExists exists = new UsernameAlreadyExists(usernameAlreadyExists.getMessage());
        return new ResponseEntity<>(exists, HttpStatus.BAD_REQUEST);
    }

}
