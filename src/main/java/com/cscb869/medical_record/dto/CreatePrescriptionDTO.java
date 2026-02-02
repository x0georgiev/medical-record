package com.cscb869.medical_record.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreatePrescriptionDTO {

    @NotNull(message = "Medicine is required")
    private Long medicineId;

    @NotBlank(message = "Dosage is required")
    @Size(max = 100, message = "Dosage must not exceed 100 characters")
    private String dosage;

    @NotBlank(message = "Frequency is required")
    @Size(max = 100, message = "Frequency must not exceed 100 characters")
    private String frequency;

    @Min(value = 1, message = "Duration must be at least 1 day")
    private Integer durationDays;

    @Size(max = 500, message = "Instructions must not exceed 500 characters")
    private String instructions;
}
