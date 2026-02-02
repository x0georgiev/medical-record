package com.cscb869.medical_record.service.impl;

import com.cscb869.medical_record.data.entity.*;
import com.cscb869.medical_record.data.repo.*;
import com.cscb869.medical_record.dto.CreateExaminationDTO;
import com.cscb869.medical_record.dto.ExaminationDTO;
import com.cscb869.medical_record.service.ExaminationService;
import com.cscb869.medical_record.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ExaminationServiceImpl implements ExaminationService {

    private final ExaminationRepository examinationRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final MedicineRepository medicineRepository;
    private final MapperUtil mapperUtil;

    @Override
    @Transactional(readOnly = true)
    public List<ExaminationDTO> getAllExaminations() {
        return mapperUtil.mapList(examinationRepository.findAll(), ExaminationDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ExaminationDTO getExaminationById(Long id) {
        Examination examination = examinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Examination not found with id: " + id));
        return mapperUtil.getModelMapper().map(examination, ExaminationDTO.class);
    }

    @Override
    public ExaminationDTO createExamination(CreateExaminationDTO createExaminationDTO) {
        Patient patient = patientRepository.findById(createExaminationDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        
        Doctor doctor = doctorRepository.findById(createExaminationDTO.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        
        Diagnosis diagnosis = diagnosisRepository.findById(createExaminationDTO.getDiagnosisId())
                .orElseThrow(() -> new RuntimeException("Diagnosis not found"));
        
        Examination examination = new Examination();
        examination.setPatient(patient);
        examination.setDoctor(doctor);
        examination.setExaminationDate(createExaminationDTO.getExaminationDate());
        examination.setDiagnosis(diagnosis);
        
        // Save examination first
        Examination savedExamination = examinationRepository.save(examination);
        
        // Create prescriptions if provided
        if (createExaminationDTO.getPrescriptions() != null && !createExaminationDTO.getPrescriptions().isEmpty()) {
            Set<Prescription> prescriptions = new HashSet<>();
            
            createExaminationDTO.getPrescriptions().forEach(prescriptionDTO -> {
                Medicine medicine = medicineRepository.findById(prescriptionDTO.getMedicineId())
                        .orElseThrow(() -> new RuntimeException("Medicine not found"));
                
                Prescription prescription = new Prescription();
                prescription.setExamination(savedExamination);
                prescription.setMedicine(medicine);
                prescription.setDosage(prescriptionDTO.getDosage());
                prescription.setFrequency(prescriptionDTO.getFrequency());
                prescription.setDurationDays(prescriptionDTO.getDurationDays());
                prescription.setInstructions(prescriptionDTO.getInstructions());
                
                prescriptions.add(prescription);
            });
            
            savedExamination.setPrescriptions(prescriptions);
            savedExamination = examinationRepository.save(savedExamination);
        }
        
        return mapperUtil.getModelMapper().map(savedExamination, ExaminationDTO.class);
    }

    @Override
    public ExaminationDTO updateExamination(Long id, CreateExaminationDTO createExaminationDTO) {
        Patient patient = patientRepository.findById(createExaminationDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        
        Doctor doctor = doctorRepository.findById(createExaminationDTO.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        
        Diagnosis diagnosis = diagnosisRepository.findById(createExaminationDTO.getDiagnosisId())
                .orElseThrow(() -> new RuntimeException("Diagnosis not found"));
        
        return examinationRepository.findById(id)
                .map(examination -> {
                    examination.setPatient(patient);
                    examination.setDoctor(doctor);
                    examination.setExaminationDate(createExaminationDTO.getExaminationDate());
                    examination.setDiagnosis(diagnosis);
                    return mapperUtil.getModelMapper().map(examinationRepository.save(examination), ExaminationDTO.class);
                }).orElseThrow(() -> new RuntimeException("Examination not found with id: " + id));
    }

    @Override
    public void deleteExamination(Long id) {
        if (!examinationRepository.existsById(id)) {
            throw new RuntimeException("Examination not found with id: " + id);
        }
        examinationRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExaminationDTO> getExaminationsByPatient(Long patientId) {
        return mapperUtil.mapList(
                examinationRepository.findByPatientIdOrderByExaminationDateDesc(patientId), 
                ExaminationDTO.class
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExaminationDTO> getExaminationsByDoctor(Long doctorId) {
        return mapperUtil.mapList(
                examinationRepository.findByDoctorIdOrderByExaminationDateDesc(doctorId), 
                ExaminationDTO.class
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExaminationDTO> getExaminationsByDateRange(LocalDate startDate, LocalDate endDate) {
        return mapperUtil.mapList(
                examinationRepository.findByDateRange(startDate, endDate), 
                ExaminationDTO.class
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExaminationDTO> getExaminationsByDoctorAndDateRange(Long doctorId, LocalDate startDate, LocalDate endDate) {
        return mapperUtil.mapList(
                examinationRepository.findByDoctorAndDateRange(doctorId, startDate, endDate), 
                ExaminationDTO.class
        );
    }
}
