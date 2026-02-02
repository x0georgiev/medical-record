package com.cscb869.medical_record.service.impl;

import com.cscb869.medical_record.data.entity.Doctor;
import com.cscb869.medical_record.data.entity.Patient;
import com.cscb869.medical_record.data.repo.DoctorRepository;
import com.cscb869.medical_record.data.repo.PatientRepository;
import com.cscb869.medical_record.dto.CreatePatientDTO;
import com.cscb869.medical_record.dto.PatientDTO;
import com.cscb869.medical_record.service.PatientService;
import com.cscb869.medical_record.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final MapperUtil mapperUtil;

    @Override
    @Transactional(readOnly = true)
    public List<PatientDTO> getAllPatients() {
        return mapperUtil.mapList(patientRepository.findAll(), PatientDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
        return mapperUtil.getModelMapper().map(patient, PatientDTO.class);
    }

    @Override
    public PatientDTO createPatient(CreatePatientDTO createPatientDTO) {
        Doctor gp = doctorRepository.findById(createPatientDTO.getGeneralPractitionerId())
                .orElseThrow(() -> new RuntimeException("General practitioner not found"));
        
        Patient patient = mapperUtil.getModelMapper().map(createPatientDTO, Patient.class);
        patient.setGeneralPractitioner(gp);
        
        Patient savedPatient = patientRepository.save(patient);
        return mapperUtil.getModelMapper().map(savedPatient, PatientDTO.class);
    }

    @Override
    public PatientDTO updatePatient(Long id, CreatePatientDTO createPatientDTO) {
        Doctor gp = doctorRepository.findById(createPatientDTO.getGeneralPractitionerId())
                .orElseThrow(() -> new RuntimeException("General practitioner not found"));
        
        return patientRepository.findById(id)
                .map(patient -> {
                    patient.setName(createPatientDTO.getName());
                    patient.setEgn(createPatientDTO.getEgn());
                    patient.setHealthInsurancePaid(createPatientDTO.isHealthInsurancePaid());
                    patient.setLastInsurancePaymentDate(createPatientDTO.getLastInsurancePaymentDate());
                    patient.setGeneralPractitioner(gp);
                    return mapperUtil.getModelMapper().map(patientRepository.save(patient), PatientDTO.class);
                }).orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }

    @Override
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientDTO> getPatientsByGeneralPractitioner(Long doctorId) {
        return mapperUtil.mapList(patientRepository.findByGeneralPractitionerId(doctorId), PatientDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientDTO> getPatientsByDiagnosis(Long diagnosisId) {
        return mapperUtil.mapList(patientRepository.findByDiagnosisId(diagnosisId), PatientDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getPatientCountPerGeneralPractitioner() {
        return patientRepository.getPatientCountPerGeneralPractitioner();
    }
}
