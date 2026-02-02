package com.cscb869.medical_record.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient extends BaseEntity {

    @NotBlank(message = "Name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @NotBlank(message = "EGN is required")
    @Pattern(regexp = "\\d{10}", message = "EGN must be exactly 10 digits")
    @Column(name = "egn", nullable = false, unique = true, length = 10)
    private String egn;

    @Column(name = "health_insurance_paid", nullable = false)
    private boolean healthInsurancePaid;

    @Column(name = "last_insurance_payment_date")
    private LocalDate lastInsurancePaymentDate;

    @NotNull(message = "Patient must have a general practitioner")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_practitioner_id", nullable = false)
    private Doctor generalPractitioner;

    // Examinations for this patient
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private Set<Examination> examinations;

    /**
     * Check if health insurance is valid (paid within last 6 months)
     */
    @Transient
    public boolean isInsuranceValid() {
        if (!healthInsurancePaid || lastInsurancePaymentDate == null) {
            return false;
        }
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        return lastInsurancePaymentDate.isAfter(sixMonthsAgo) || 
               lastInsurancePaymentDate.isEqual(sixMonthsAgo);
    }
}
