package com.cscb869.medical_record.data.repo;

import com.cscb869.medical_record.data.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

    Diagnosis findByCode(String code);

    // Report: Find most common diagnoses (ordered by count)
    @Query("SELECT d.id, d.code, d.name, COUNT(e.id) as diagnosisCount " +
           "FROM Diagnosis d " +
           "LEFT JOIN d.examinations e " +
           "GROUP BY d.id, d.code, d.name " +
           "ORDER BY diagnosisCount DESC")
    List<Object[]> getMostCommonDiagnoses();
}
