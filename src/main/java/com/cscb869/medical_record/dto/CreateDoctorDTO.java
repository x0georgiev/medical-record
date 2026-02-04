package com.cscb869.medical_record.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateDoctorDTO {

    @NotBlank(message = "Unique identifier is required")
    @Size(max = 50, message = "Unique identifier must not exceed 50 characters")
    private String uniqueIdentifier;

    @NotBlank(message = "Name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    private String name;

    @NotBlank(message = "Specialty is required")
    @Size(max = 100, message = "Specialty must not exceed 100 characters")
    private String specialty;

    @JsonProperty("generalPractitioner")
    private boolean generalPractitioner;
}
