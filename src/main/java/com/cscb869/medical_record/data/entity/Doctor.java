package com.cscb869.medical_record.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor extends BaseEntity {

    @NotBlank(message = "Unique identifier is required")
    @Size(max = 50, message = "Unique identifier must not exceed 50 characters")
    @Column(name = "unique_identifier", nullable = false, unique = true, length = 50)
    private String uniqueIdentifier;

    @NotBlank(message = "Name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @NotBlank(message = "Specialty is required")
    @Size(max = 100, message = "Specialty must not exceed 100 characters")
    @Column(name = "specialty", nullable = false, length = 100)
    private String specialty;

    @Column(name = "is_general_practitioner", nullable = false)
    private boolean generalPractitioner;

    // Patients who have this doctor as their GP
    @OneToMany(mappedBy = "generalPractitioner")
    @JsonIgnore
    private Set<Patient> patients;

    // Examinations conducted by this doctor
    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private Set<Examination> examinations;
}
