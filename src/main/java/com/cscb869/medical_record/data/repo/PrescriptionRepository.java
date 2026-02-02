package com.cscb869.medical_record.data.repo;

import com.cscb869.medical_record.data.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findByExaminationId(Long examinationId);

    List<Prescription> findByMedicineId(Long medicineId);

    // Find all prescriptions for a patient
    @Query("SELECT p FROM Prescription p " +
           "WHERE p.examination.patient.id = :patientId " +
           "ORDER BY p.examination.examinationDate DESC")
    List<Prescription> findByPatientId(@Param("patientId") Long patientId);

    // Find all prescriptions by a doctor
    @Query("SELECT p FROM Prescription p " +
           "WHERE p.examination.doctor.id = :doctorId " +
           "ORDER BY p.examination.examinationDate DESC")
    List<Prescription> findByDoctorId(@Param("doctorId") Long doctorId);

    // Find prescriptions by date range
    @Query("SELECT p FROM Prescription p " +
           "WHERE p.examination.examinationDate BETWEEN :startDate AND :endDate " +
           "ORDER BY p.examination.examinationDate DESC")
    List<Prescription> findByDateRange(@Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate);
}
