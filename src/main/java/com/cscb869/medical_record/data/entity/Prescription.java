package com.cscb869.medical_record.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "prescriptions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription extends BaseEntity {

    @NotNull(message = "Examination is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examination_id", nullable = false)
    private Examination examination;

    @NotNull(message = "Medicine is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @NotBlank(message = "Dosage is required")
    @Size(max = 100, message = "Dosage must not exceed 100 characters")
    @Column(name = "dosage", nullable = false, length = 100)
    private String dosage;

    @NotBlank(message = "Frequency is required")
    @Size(max = 100, message = "Frequency must not exceed 100 characters")
    @Column(name = "frequency", nullable = false, length = 100)
    private String frequency;

    @Min(value = 1, message = "Duration must be at least 1 day")
    @Column(name = "duration_days")
    private Integer durationDays;

    @Column(name = "instructions", length = 500)
    private String instructions;
}
