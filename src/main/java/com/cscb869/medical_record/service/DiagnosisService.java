package com.cscb869.medical_record.service;

import com.cscb869.medical_record.dto.CreateDiagnosisDTO;
import com.cscb869.medical_record.dto.DiagnosisDTO;

import java.util.List;

public interface DiagnosisService {

    List<DiagnosisDTO> getAllDiagnoses();

    DiagnosisDTO getDiagnosisById(Long id);

    DiagnosisDTO createDiagnosis(CreateDiagnosisDTO createDiagnosisDTO);

    DiagnosisDTO updateDiagnosis(Long id, CreateDiagnosisDTO createDiagnosisDTO);

    void deleteDiagnosis(Long id);

    List<Object[]> getMostCommonDiagnoses();
}
