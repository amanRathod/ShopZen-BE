package com.ecommerce.ShopZenbe.common.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleException(ResourceNotFoundException e,
                                                    HttpServletRequest request) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                "",
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({DuplicateResourceException.class, RequestValidationException.class})
    public ResponseEntity<ApiError> handleException(DuplicateResourceException e,
                                                    HttpServletRequest request) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                "",
                HttpStatus.FORBIDDEN.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {

        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                "Validation failed for some fields",
                errors,
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
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
                "",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
