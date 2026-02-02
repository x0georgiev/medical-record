package com.cscb869.medical_record.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for web/view layer (Thymeleaf)
 * Handles exceptions and returns error view pages
 */
@ControllerAdvice
public class ExceptionsHandler {

//    @ExceptionHandler(Exception.class)
//    protected String handleException(Exception exception, Model model) {
//        model.addAttribute("message", exception.getMessage());
//        return "/errors/errors";
//    }
//
//    @ExceptionHandler({
//        DoctorNotFoundException.class,
//        PatientNotFoundException.class,
//        DiagnosisNotFoundException.class,
//        ExaminationNotFoundException.class,
//        MedicineNotFoundException.class,
//        PrescriptionNotFoundException.class,
//        SickLeaveNotFoundException.class
//    })
//    public String handleNotFoundException(RuntimeException exception, Model model) {
//        model.addAttribute("message", exception.getMessage());
//        return "/errors/not-found-errors";
//    }
}
