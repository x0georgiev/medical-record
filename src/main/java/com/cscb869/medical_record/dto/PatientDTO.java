package com.cscb869.medical_record.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PatientDTO {
    private Long id;
    private String name;
    private String egn;
    private boolean healthInsurancePaid;
    private LocalDate lastInsurancePaymentDate;
    private Long generalPractitionerId;
    private String generalPractitionerName;
    private boolean insuranceValid;
}
