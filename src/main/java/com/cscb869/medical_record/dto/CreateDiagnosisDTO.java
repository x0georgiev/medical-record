package com.cscb869.medical_record.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateDiagnosisDTO {

    @NotBlank(message = "Diagnosis code is required")
    @Size(max = 20, message = "Diagnosis code must not exceed 20 characters")
    private String code;

    @NotBlank(message = "Diagnosis name is required")
    @Size(max = 200, message = "Diagnosis name must not exceed 200 characters")
    private String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
}
