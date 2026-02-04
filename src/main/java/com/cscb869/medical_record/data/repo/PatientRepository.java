package com.cscb869.medical_record.data.repo;

import com.cscb869.medical_record.data.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findByEgn(String egn);

    List<Patient> findByGeneralPractitionerId(Long generalPractitionerId);

    // Report: Find patients with a specific diagnosis
    @Query("SELECT DISTINCT p FROM Patient p " +
           "JOIN p.examinations e " +
           "WHERE e.diagnosis.id = :diagnosisId")
    List<Patient> findByDiagnosisId(@Param("diagnosisId") Long diagnosisId);

    // Report: Get count of patients per general practitioner
    @Query("SELECT gp.id, gp.name, COUNT(p.id) as patientCount " +
           "FROM Doctor gp " +
           "LEFT JOIN Patient p ON p.generalPractitioner.id = gp.id " +
           "WHERE gp.generalPractitioner = true " +
           "GROUP BY gp.id, gp.name " +
           "ORDER BY patientCount DESC")
    List<Object[]> getPatientCountPerGeneralPractitioner();
}
