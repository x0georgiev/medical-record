package com.cscb869.medical_record.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DoctorDTO {
    private Long id;
    private String uniqueIdentifier;
    private String name;
    private String specialty;
    private boolean isGeneralPractitioner;
}
