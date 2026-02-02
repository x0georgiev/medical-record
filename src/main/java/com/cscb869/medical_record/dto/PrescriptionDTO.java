package com.cscb869.medical_record.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PrescriptionDTO {
    private Long id;
    private Long examinationId;
    private Long medicineId;
    private String medicineName;
    private String dosage;
    private String frequency;
    private Integer durationDays;
    private String instructions;
}
