package com.cscb869.medical_record.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateSickLeaveDTO {

    @NotNull(message = "Examination is required")
    private Long examinationId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 day")
    private Integer durationDays;

    @NotNull(message = "Issue date is required")
    private LocalDate issueDate;
}
