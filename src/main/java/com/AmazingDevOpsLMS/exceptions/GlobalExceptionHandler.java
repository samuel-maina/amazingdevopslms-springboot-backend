package com.AmazingDevOpsLMS.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Restful global exception handler
 *
 * GlobalExceptionHandler.java
 *
 * @author samuel Maina
 *
 * 03-10-2022
 *
 * @version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception handler for creating a record that exists
     *
     * @return ExceptionObject and HttpStatus.CONFLICT
     */
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<?> resourceAlreadyExists() {

        return new ResponseEntity<>("Resource already exists", HttpStatus.CONFLICT);
    }

    /**
     * Exception handler for ResourceNotFoundException
     *
     * @return ResponseEntity HttpStatus.NOT_FOUND
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> ResourceDoesNotExist() {

        return new ResponseEntity<>("Resource not found", HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for SQLIntegrityConstraintViolationException
     *
     * @return exception body, HttpStatus.FORBIDDEN
     */
    @ExceptionHandler(java.sql.SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> sQLIntegrityConstraintViolationException() {

        return new ResponseEntity<>("Action forbidden, it may interfere with data consistency", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<?> accountDisabled() {

        return new ResponseEntity<>("Your account has been disabled", HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> invalidUsernameAndPassword() {

        return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<?> EmailAlreadyRegistered() {

        return new ResponseEntity<>("Email is already registered. Do you have an account with us?", HttpStatus.FORBIDDEN);
    }
    
     @ExceptionHandler(UserActivationException.class)
    public ResponseEntity<?> ActivationCodeNotMatching() {

        return new ResponseEntity<>("The code provided does not match what we sent you.", HttpStatus.FORBIDDEN);
    }
    
      @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<?> StudentNotFound() {

        return new ResponseEntity<>("A student with the provided email address does not exist.", HttpStatus.NOT_FOUND);
    }
}
