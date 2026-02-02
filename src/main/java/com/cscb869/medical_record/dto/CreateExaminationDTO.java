package com.cscb869.medical_record.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateExaminationDTO {

    @NotNull(message = "Patient is required")
    private Long patientId;

    @NotNull(message = "Doctor is required")
    private Long doctorId;

    @NotNull(message = "Examination date is required")
    private LocalDate examinationDate;

    @NotNull(message = "Diagnosis is required")
    private Long diagnosisId;

    @Valid
    private List<CreatePrescriptionDTO> prescriptions;
}
