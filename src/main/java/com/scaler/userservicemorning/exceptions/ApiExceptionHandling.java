package com.scaler.userservicemorning.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandling {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Input fields are not valid");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        problem.setProperty("errors", errors);
        return problem;
    }


    // TODO: To debug as Homework
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDuplicate(DataIntegrityViolationException exception){
        // Get all exceptions of certain types and manage how you want to return to API
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "This email already exists");
    }
}
