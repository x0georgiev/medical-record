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
public class CreateMedicineDTO {

    @NotBlank(message = "Medicine name is required")
    @Size(max = 200, message = "Medicine name must not exceed 200 characters")
    private String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @Size(max = 200, message = "Manufacturer must not exceed 200 characters")
    private String manufacturer;
}
