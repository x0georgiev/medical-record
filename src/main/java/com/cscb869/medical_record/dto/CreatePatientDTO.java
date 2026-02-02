package com.cscb869.medical_record.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreatePatientDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    private String name;

    @NotBlank(message = "EGN is required")
    @Pattern(regexp = "\\d{10}", message = "EGN must be exactly 10 digits")
    private String egn;

    private boolean healthInsurancePaid;

    private LocalDate lastInsurancePaymentDate;

    @NotNull(message = "General practitioner is required")
    private Long generalPractitionerId;
}
