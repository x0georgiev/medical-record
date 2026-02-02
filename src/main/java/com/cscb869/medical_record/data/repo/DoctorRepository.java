package com.cscb869.medical_record.data.repo;

import com.cscb869.medical_record.data.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByUniqueIdentifier(String uniqueIdentifier);

    List<Doctor> findByIsGeneralPractitionerTrue();

    // Report: Get count of visits (examinations) for each doctor
    @Query("SELECT d.id, d.name, COUNT(e.id) as visitCount " +
           "FROM Doctor d LEFT JOIN d.examinations e " +
           "GROUP BY d.id, d.name " +
           "ORDER BY visitCount DESC")
    List<Object[]> getVisitCountPerDoctor();

    // Report: Get doctors who issued the most sick leaves
    @Query("SELECT d.id, d.name, COUNT(sl.id) as sickLeaveCount " +
           "FROM Doctor d " +
           "JOIN d.examinations e " +
           "JOIN e.sickLeave sl " +
           "GROUP BY d.id, d.name " +
           "ORDER BY sickLeaveCount DESC")
    List<Object[]> getDoctorsWithMostSickLeaves();
}
