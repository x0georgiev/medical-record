package com.cscb869.medical_record.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MedicineDTO {
    private Long id;
    private String name;
    private String description;
    private String manufacturer;
}
