package com.cscb869.medical_record.service;

import com.cscb869.medical_record.dto.CreateExaminationDTO;
import com.cscb869.medical_record.dto.ExaminationDTO;

import java.time.LocalDate;
import java.util.List;

public interface ExaminationService {

    List<ExaminationDTO> getAllExaminations();

    ExaminationDTO getExaminationById(Long id);

    ExaminationDTO createExamination(CreateExaminationDTO createExaminationDTO);

    ExaminationDTO updateExamination(Long id, CreateExaminationDTO createExaminationDTO);

    void deleteExamination(Long id);

    List<ExaminationDTO> getExaminationsByPatient(Long patientId);

    List<ExaminationDTO> getExaminationsByDoctor(Long doctorId);

    List<ExaminationDTO> getExaminationsByDateRange(LocalDate startDate, LocalDate endDate);

    List<ExaminationDTO> getExaminationsByDoctorAndDateRange(Long doctorId, LocalDate startDate, LocalDate endDate);
}
