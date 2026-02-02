package com.cscb869.medical_record.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DiagnosisDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
}
