package com.cscb869.medical_record.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.security.sasl.AuthenticationException;

/**
 * Global exception handler for REST API layer
 * Handles exceptions and returns simple string responses
 */
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "Authentication Failed!", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception exception, WebRequest request) {
        return new ResponseEntity<Object>(
                "Access denied for this user!", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
        DoctorNotFoundException.class,
        PatientNotFoundException.class,
        DiagnosisNotFoundException.class,
        ExaminationNotFoundException.class,
        MedicineNotFoundException.class,
        PrescriptionNotFoundException.class,
        SickLeaveNotFoundException.class
    })
    public ResponseEntity<Object> handleNotFoundException(RuntimeException exception, WebRequest request) {
        String responseBody = "Object Not Found!";
        return handleExceptionInternal(exception, responseBody,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
