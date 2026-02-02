package com.cscb869.medical_record.data.repo;

import com.cscb869.medical_record.data.entity.Examination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long> {

    List<Examination> findByPatientIdOrderByExaminationDateDesc(Long patientId);

    List<Examination> findByDoctorIdOrderByExaminationDateDesc(Long doctorId);

    List<Examination> findByDiagnosisId(Long diagnosisId);

    // Report: Find examinations in a date range
    @Query("SELECT e FROM Examination e " +
           "WHERE e.examinationDate BETWEEN :startDate AND :endDate " +
           "ORDER BY e.examinationDate DESC")
    List<Examination> findByDateRange(@Param("startDate") LocalDate startDate, 
                                      @Param("endDate") LocalDate endDate);

    // Report: Find examinations by doctor in a date range
    @Query("SELECT e FROM Examination e " +
           "WHERE e.doctor.id = :doctorId " +
           "AND e.examinationDate BETWEEN :startDate AND :endDate " +
           "ORDER BY e.examinationDate DESC")
    List<Examination> findByDoctorAndDateRange(@Param("doctorId") Long doctorId,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);
}
