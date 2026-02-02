package com.cscb869.medical_record.data.repo;

import com.cscb869.medical_record.data.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    List<Medicine> findByNameContaining(String name);

    List<Medicine> findByNameStartsWith(String name);

    // Report: Most prescribed medicines
    @Query("SELECT m.id, m.name, COUNT(p.id) as prescriptionCount " +
           "FROM Medicine m " +
           "LEFT JOIN m.prescriptions p " +
           "GROUP BY m.id, m.name " +
           "ORDER BY prescriptionCount DESC")
    List<Object[]> getMostPrescribedMedicines();
}
