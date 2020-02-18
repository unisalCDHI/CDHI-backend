package com.cdhi.controllers.exceptions;

import com.cdhi.services.exceptions.ObjectAlreadyExistsException;
import com.cdhi.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), Collections.singletonList(new Error(e.getMessage())), HttpStatus.NOT_FOUND.name(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(ObjectAlreadyExistsException.class)
    public ResponseEntity<StandardError> objectAlreadyExistsException(ObjectAlreadyExistsException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.CONFLICT.value(), Collections.singletonList(new Error(e.getMessage())), HttpStatus.CONFLICT.name(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }
}
