package com.cscb869.medical_record.service;

import com.cscb869.medical_record.dto.CreateMedicineDTO;
import com.cscb869.medical_record.dto.MedicineDTO;

import java.util.List;

public interface MedicineService {

    List<MedicineDTO> getAllMedicines();

    MedicineDTO getMedicineById(Long id);

    MedicineDTO createMedicine(CreateMedicineDTO createMedicineDTO);

    MedicineDTO updateMedicine(Long id, CreateMedicineDTO createMedicineDTO);

    void deleteMedicine(Long id);

    List<MedicineDTO> searchMedicines(String name);

    List<Object[]> getMostPrescribedMedicines();
}
