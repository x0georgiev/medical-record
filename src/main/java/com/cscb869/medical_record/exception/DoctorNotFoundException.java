package com.cscb869.medical_record.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DoctorNotFoundException extends RuntimeException {
    
    public DoctorNotFoundException(String message) {
        super(message);
    }
}
