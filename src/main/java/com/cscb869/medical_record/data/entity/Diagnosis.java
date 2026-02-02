package com.cscb869.medical_record.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "diagnoses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diagnosis extends BaseEntity {

    @NotBlank(message = "Diagnosis code is required")
    @Size(max = 20, message = "Diagnosis code must not exceed 20 characters")
    @Column(name = "code", nullable = false, unique = true, length = 20)
    private String code;

    @NotBlank(message = "Diagnosis name is required")
    @Size(max = 200, message = "Diagnosis name must not exceed 200 characters")
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    // Examinations with this diagnosis
    @OneToMany(mappedBy = "diagnosis")
    @JsonIgnore
    private Set<Examination> examinations;
}
