package com.cscb869.medical_record.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExaminationDTO {
    private Long id;
    private Long patientId;
    private String patientName;
    private Long doctorId;
    private String doctorName;
    private LocalDate examinationDate;
    private Long diagnosisId;
    private String diagnosisName;
    private List<PrescriptionDTO> prescriptions;
    private SickLeaveDTO sickLeave;
}
