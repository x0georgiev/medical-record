package com.cscb869.medical_record.service;

import com.cscb869.medical_record.dto.CreatePatientDTO;
import com.cscb869.medical_record.dto.PatientDTO;

import java.util.List;

public interface PatientService {

    List<PatientDTO> getAllPatients();

    PatientDTO getPatientById(Long id);

    PatientDTO createPatient(CreatePatientDTO createPatientDTO);

    PatientDTO updatePatient(Long id, CreatePatientDTO createPatientDTO);

    void deletePatient(Long id);

    List<PatientDTO> getPatientsByGeneralPractitioner(Long doctorId);

    List<PatientDTO> getPatientsByDiagnosis(Long diagnosisId);

    List<Object[]> getPatientCountPerGeneralPractitioner();
}
