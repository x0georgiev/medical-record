package com.cscb869.medical_record.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

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

    // Prescriptions (medicines) for this examination
    @OneToMany(mappedBy = "examination", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Prescription> prescriptions;

    // One examination can have at most one sick leave
    @OneToOne(mappedBy = "examination", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private SickLeave sickLeave;
}
