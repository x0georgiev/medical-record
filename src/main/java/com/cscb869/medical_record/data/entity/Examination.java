package com.cscb869.medical_record.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "examinations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Examination extends BaseEntity {

    @NotNull(message = "Patient is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NotNull(message = "Doctor is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @NotNull(message = "Examination date is required")
    @Column(name = "examination_date", nullable = false)
    private LocalDate examinationDate;

    @NotNull(message = "Diagnosis is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id", nullable = false)
    private Diagnosis diagnosis;

    @Column(name = "treatment", length = 2000)
    private String treatment;

    @Column(name = "notes", length = 2000)
    private String notes;

    // One examination can have at most one sick leave
    @OneToOne(mappedBy = "examination", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private SickLeave sickLeave;
}
