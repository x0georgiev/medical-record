package com.cscb869.medical_record.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MedicineNotFoundException extends RuntimeException {
    
    public MedicineNotFoundException(String message) {
        super(message);
    }
}
