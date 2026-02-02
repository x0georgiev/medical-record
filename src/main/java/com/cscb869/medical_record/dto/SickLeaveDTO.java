package com.cscb869.medical_record.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SickLeaveDTO {
    private Long id;
    private Long examinationId;
    private LocalDate startDate;
    private Integer durationDays;
    private LocalDate issueDate;
    private LocalDate endDate;
}
