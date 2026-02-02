package com.cscb869.medical_record.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ExaminationNotFoundException extends RuntimeException {
    
    public ExaminationNotFoundException(String message) {
        super(message);
    }
}
