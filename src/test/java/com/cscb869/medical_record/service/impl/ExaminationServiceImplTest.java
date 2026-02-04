package com.cscb869.medical_record.service.impl;

import com.cscb869.medical_record.data.entity.*;
import com.cscb869.medical_record.data.repo.*;
import com.cscb869.medical_record.dto.ExaminationDTO;
import com.cscb869.medical_record.exception.ExaminationNotFoundException;
import com.cscb869.medical_record.util.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class ExaminationServiceImplTest {

    @MockitoBean
    private ExaminationRepository examinationRepository;

    @MockitoBean
    private PatientRepository patientRepository;

    @MockitoBean
    private DoctorRepository doctorRepository;

    @MockitoBean
    private DiagnosisRepository diagnosisRepository;

    @MockitoBean
    private MedicineRepository medicineRepository;

    @Autowired
    private ExaminationServiceImpl examinationService;

    @Autowired
    private MapperUtil mapperUtil;

    private Examination examination;
    private Patient patient;
    private Doctor doctor;
    private Diagnosis diagnosis;

    @BeforeEach
    public void init() {
        doctor = Doctor.builder()
                .uniqueIdentifier("DOC-001")
                .name("Dr. Ivan Petrov")
                .specialty("General Medicine")
                .generalPractitioner(true)
                .build();
        doctor.setId(1L);

        patient = Patient.builder()
                .name("Petar Georgiev")
                .egn("8501011234")
                .healthInsurancePaid(true)
                .generalPractitioner(doctor)
                .build();
        patient.setId(1L);

        diagnosis = Diagnosis.builder()
                .code("J06.9")
                .name("Acute upper respiratory infection")
                .description("Common cold")
                .build();
        diagnosis.setId(1L);

        examination = Examination.builder()
                .patient(patient)
                .doctor(doctor)
                .examinationDate(LocalDate.of(2025, 11, 15))
                .diagnosis(diagnosis)
                .build();
        examination.setId(1L);
    }

    @Test
    void getExaminationByIdSuccess() {
        given(examinationRepository.findById(1L))
                .willReturn(Optional.of(examination));

        ExaminationDTO result = examinationService.getExaminationById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getExaminationDate()).isEqualTo(LocalDate.of(2025, 11, 15));
    }

    @Test
    void getExaminationByIdNotFound() {
        given(examinationRepository.findById(999L))
                .willReturn(Optional.empty());

        assertThrows(ExaminationNotFoundException.class,
                () -> examinationService.getExaminationById(999L));
    }

    @Test
    void getAllExaminations() {
        Examination exam2 = Examination.builder()
                .patient(patient)
                .doctor(doctor)
                .examinationDate(LocalDate.of(2025, 12, 1))
                .diagnosis(diagnosis)
                .build();
        exam2.setId(2L);

        List<Examination> examinations = Arrays.asList(examination, exam2);

        given(examinationRepository.findAll()).willReturn(examinations);

        List<ExaminationDTO> result = examinationService.getAllExaminations();

        assertThat(result).isNotNull();
        assertEquals(2, result.size());
    }

    @Test
    void deleteExaminationSuccess() {
        given(examinationRepository.existsById(1L)).willReturn(true);
        doNothing().when(examinationRepository).deleteById(1L);

        assertDoesNotThrow(() -> examinationService.deleteExamination(1L));
        verify(examinationRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteExaminationNotFound() {
        given(examinationRepository.existsById(999L)).willReturn(false);

        assertThrows(ExaminationNotFoundException.class,
                () -> examinationService.deleteExamination(999L));
    }

    @Test
    void getExaminationsByPatient() {
        List<Examination> examinations = Arrays.asList(examination);

        given(examinationRepository.findByPatientIdOrderByExaminationDateDesc(1L))
                .willReturn(examinations);

        List<ExaminationDTO> result = examinationService.getExaminationsByPatient(1L);

        assertThat(result).isNotNull();
        assertEquals(1, result.size());
    }

    @Test
    void getExaminationsByDoctor() {
        List<Examination> examinations = Arrays.asList(examination);

        given(examinationRepository.findByDoctorIdOrderByExaminationDateDesc(1L))
                .willReturn(examinations);

        List<ExaminationDTO> result = examinationService.getExaminationsByDoctor(1L);

        assertThat(result).isNotNull();
        assertEquals(1, result.size());
    }

    @Test
    void getExaminationsByDateRange() {
        List<Examination> examinations = Arrays.asList(examination);

        LocalDate startDate = LocalDate.of(2025, 11, 1);
        LocalDate endDate = LocalDate.of(2025, 11, 30);

        given(examinationRepository.findByDateRange(startDate, endDate))
                .willReturn(examinations);

        List<ExaminationDTO> result = examinationService.getExaminationsByDateRange(startDate, endDate);

        assertThat(result).isNotNull();
        assertEquals(1, result.size());
    }
}
