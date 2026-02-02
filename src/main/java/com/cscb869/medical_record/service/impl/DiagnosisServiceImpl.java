package com.cscb869.medical_record.service.impl;

import com.cscb869.medical_record.data.entity.Diagnosis;
import com.cscb869.medical_record.data.repo.DiagnosisRepository;
import com.cscb869.medical_record.dto.CreateDiagnosisDTO;
import com.cscb869.medical_record.dto.DiagnosisDTO;
import com.cscb869.medical_record.service.DiagnosisService;
import com.cscb869.medical_record.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DiagnosisServiceImpl implements DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final MapperUtil mapperUtil;

    @Override
    @Transactional(readOnly = true)
    public List<DiagnosisDTO> getAllDiagnoses() {
        return mapperUtil.mapList(diagnosisRepository.findAll(), DiagnosisDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public DiagnosisDTO getDiagnosisById(Long id) {
        Diagnosis diagnosis = diagnosisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diagnosis not found with id: " + id));
        return mapperUtil.getModelMapper().map(diagnosis, DiagnosisDTO.class);
    }

    @Override
    public DiagnosisDTO createDiagnosis(CreateDiagnosisDTO createDiagnosisDTO) {
        Diagnosis diagnosis = mapperUtil.getModelMapper().map(createDiagnosisDTO, Diagnosis.class);
        Diagnosis savedDiagnosis = diagnosisRepository.save(diagnosis);
        return mapperUtil.getModelMapper().map(savedDiagnosis, DiagnosisDTO.class);
    }

    @Override
    public DiagnosisDTO updateDiagnosis(Long id, CreateDiagnosisDTO createDiagnosisDTO) {
        return diagnosisRepository.findById(id)
                .map(diagnosis -> {
                    diagnosis.setCode(createDiagnosisDTO.getCode());
                    diagnosis.setName(createDiagnosisDTO.getName());
                    diagnosis.setDescription(createDiagnosisDTO.getDescription());
                    return mapperUtil.getModelMapper().map(diagnosisRepository.save(diagnosis), DiagnosisDTO.class);
                }).orElseThrow(() -> new RuntimeException("Diagnosis not found with id: " + id));
    }

    @Override
    public void deleteDiagnosis(Long id) {
        if (!diagnosisRepository.existsById(id)) {
            throw new RuntimeException("Diagnosis not found with id: " + id);
        }
        diagnosisRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getMostCommonDiagnoses() {
        return diagnosisRepository.getMostCommonDiagnoses();
    }
}
