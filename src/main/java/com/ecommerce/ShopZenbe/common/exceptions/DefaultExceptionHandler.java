package com.ecommerce.ShopZenbe.common.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleException(ResourceNotFoundException e,
                                                    HttpServletRequest request) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                new ArrayList<>(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());

        List<String> errors = e.getBindingResult()
                .getFieldErrors() // extracts the FieldError objects from the BindingResult
                .stream()
                .map(FieldError::getDefaultMessage)// For each FieldError object, the method extracts the default error message.
                .collect(Collectors.toList());

        errorResponse.put("message", "Validation failed for some fields");
        errorResponse.put("errors", errors);

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                errors,
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(InsufficientAuthenticationException.class)
//    public ResponseEntity<ApiError> handleException(InsufficientAuthenticationException e,
//                                                    HttpServletRequest request) {
//        ApiError apiError = new ApiError(
//                request.getRequestURI(),
//                e.getMessage(),
//                HttpStatus.FORBIDDEN.value(),
//                LocalDateTime.now()
//        );
//
//        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
//    }

//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<ApiError> handleException(BadCredentialsException e,
//                                                    HttpServletRequest request) {
//        ApiError apiError = new ApiError(
//                request.getRequestURI(),
//                e.getMessage(),
//                HttpStatus.UNAUTHORIZED.value(),
//                LocalDateTime.now()
//        );
//
//        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e,
                                                    HttpServletRequest request) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                new ArrayList<>(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
