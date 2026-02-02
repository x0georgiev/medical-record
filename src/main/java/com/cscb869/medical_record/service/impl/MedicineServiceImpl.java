package com.cscb869.medical_record.service.impl;

import com.cscb869.medical_record.data.entity.Medicine;
import com.cscb869.medical_record.data.repo.MedicineRepository;
import com.cscb869.medical_record.dto.CreateMedicineDTO;
import com.cscb869.medical_record.dto.MedicineDTO;
import com.cscb869.medical_record.service.MedicineService;
import com.cscb869.medical_record.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;
    private final MapperUtil mapperUtil;

    @Override
    @Transactional(readOnly = true)
    public List<MedicineDTO> getAllMedicines() {
        return mapperUtil.mapList(medicineRepository.findAll(), MedicineDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public MedicineDTO getMedicineById(Long id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found with id: " + id));
        return mapperUtil.getModelMapper().map(medicine, MedicineDTO.class);
    }

    @Override
    public MedicineDTO createMedicine(CreateMedicineDTO createMedicineDTO) {
        Medicine medicine = mapperUtil.getModelMapper().map(createMedicineDTO, Medicine.class);
        Medicine savedMedicine = medicineRepository.save(medicine);
        return mapperUtil.getModelMapper().map(savedMedicine, MedicineDTO.class);
    }

    @Override
    public MedicineDTO updateMedicine(Long id, CreateMedicineDTO createMedicineDTO) {
        return medicineRepository.findById(id)
                .map(medicine -> {
                    medicine.setName(createMedicineDTO.getName());
                    medicine.setDescription(createMedicineDTO.getDescription());
                    medicine.setManufacturer(createMedicineDTO.getManufacturer());
                    return mapperUtil.getModelMapper().map(medicineRepository.save(medicine), MedicineDTO.class);
                }).orElseThrow(() -> new RuntimeException("Medicine not found with id: " + id));
    }

    @Override
    public void deleteMedicine(Long id) {
        if (!medicineRepository.existsById(id)) {
            throw new RuntimeException("Medicine not found with id: " + id);
        }
        medicineRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicineDTO> searchMedicines(String name) {
        return mapperUtil.mapList(medicineRepository.findByNameContaining(name), MedicineDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getMostPrescribedMedicines() {
        return medicineRepository.getMostPrescribedMedicines();
    }
}
