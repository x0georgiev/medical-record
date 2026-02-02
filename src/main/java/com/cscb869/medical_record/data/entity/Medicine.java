package com.cscb869.medical_record.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "medicines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine extends BaseEntity {

    @NotBlank(message = "Medicine name is required")
    @Size(max = 200, message = "Medicine name must not exceed 200 characters")
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "manufacturer", length = 200)
    private String manufacturer;

    // Prescriptions that include this medicine
    @OneToMany(mappedBy = "medicine")
    @JsonIgnore
    private Set<Prescription> prescriptions;
}
