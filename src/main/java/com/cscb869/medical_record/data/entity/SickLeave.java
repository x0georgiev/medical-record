package com.cscb869.medical_record.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "sick_leaves")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SickLeave extends BaseEntity {

    @NotNull(message = "Examination is required")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examination_id", nullable = false, unique = true)
    private Examination examination;

    @NotNull(message = "Start date is required")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull(message = "Duration in days is required")
    @Min(value = 1, message = "Duration must be at least 1 day")
    @Column(name = "duration_days", nullable = false)
    private Integer durationDays;

    @NotNull(message = "Issue date is required")
    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    /**
     * Calculate the end date of the sick leave
     */
    @Transient
    public LocalDate getEndDate() {
        return startDate.plusDays(durationDays - 1);
    }
}
