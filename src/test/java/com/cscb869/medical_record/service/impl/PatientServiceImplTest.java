package com.cscb869.medical_record.service.impl;

import com.cscb869.medical_record.data.entity.Doctor;
import com.cscb869.medical_record.data.entity.Patient;
import com.cscb869.medical_record.data.repo.DoctorRepository;
import com.cscb869.medical_record.data.repo.PatientRepository;
import com.cscb869.medical_record.dto.CreatePatientDTO;
import com.cscb869.medical_record.dto.PatientDTO;
import com.cscb869.medical_record.exception.DoctorNotFoundException;
import com.cscb869.medical_record.exception.PatientNotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class PatientServiceImplTest {

    @MockitoBean
    private PatientRepository patientRepository;

    @MockitoBean
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientServiceImpl patientService;

    @Autowired
    private MapperUtil mapperUtil;

    private Patient patient;
    private Doctor doctor;

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
                .lastInsurancePaymentDate(LocalDate.of(2025, 12, 15))
                .generalPractitioner(doctor)
                .build();
        patient.setId(1L);
    }

    @Test
    void getPatientByIdSuccess() {
        given(patientRepository.findById(1L))
                .willReturn(Optional.of(patient));

        PatientDTO result = patientService.getPatientById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Petar Georgiev");
        assertThat(result.getEgn()).isEqualTo("8501011234");
    }

    @Test
    void getPatientByIdNotFound() {
        given(patientRepository.findById(999L))
                .willReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class,
                () -> patientService.getPatientById(999L));
    }

    @Test
    void getAllPatients() {
        Patient patient2 = Patient.builder()
                .name("Stefka Marinova")
                .egn("9003152345")
                .healthInsurancePaid(true)
                .generalPractitioner(doctor)
                .build();
        patient2.setId(2L);

        List<Patient> patients = Arrays.asList(patient, patient2);

        given(patientRepository.findAll()).willReturn(patients);

        List<PatientDTO> result = patientService.getAllPatients();

        assertThat(result).isNotNull();
        assertEquals(2, result.size());
        assertEquals("Petar Georgiev", result.get(0).getName());
        assertEquals("Stefka Marinova", result.get(1).getName());
    }

    @Test
    void createPatientSuccess() {
        CreatePatientDTO createDTO = new CreatePatientDTO();
        createDTO.setName("New Patient");
        createDTO.setEgn("9901011234");
        createDTO.setHealthInsurancePaid(true);
        createDTO.setLastInsurancePaymentDate(LocalDate.of(2026, 1, 15));
        createDTO.setGeneralPractitionerId(1L);

        Patient newPatient = Patient.builder()
                .name("New Patient")
                .egn("9901011234")
                .healthInsurancePaid(true)
                .lastInsurancePaymentDate(LocalDate.of(2026, 1, 15))
                .generalPractitioner(doctor)
                .build();
        newPatient.setId(10L);

        given(doctorRepository.findById(1L)).willReturn(Optional.of(doctor));
        given(patientRepository.save(any(Patient.class))).willReturn(newPatient);

        PatientDTO result = patientService.createPatient(createDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("New Patient");
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void createPatientDoctorNotFound() {
        CreatePatientDTO createDTO = new CreatePatientDTO();
        createDTO.setName("New Patient");
        createDTO.setGeneralPractitionerId(999L);

        given(doctorRepository.findById(999L)).willReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class,
                () -> patientService.createPatient(createDTO));
    }

    @Test
    void deletePatientSuccess() {
        given(patientRepository.existsById(1L)).willReturn(true);
        doNothing().when(patientRepository).deleteById(1L);

        assertDoesNotThrow(() -> patientService.deletePatient(1L));
        verify(patientRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletePatientNotFound() {
        given(patientRepository.existsById(999L)).willReturn(false);

        assertThrows(PatientNotFoundException.class,
                () -> patientService.deletePatient(999L));
    }

    @Test
    void getPatientsByGeneralPractitioner() {
        List<Patient> patients = Arrays.asList(patient);

        given(patientRepository.findByGeneralPractitionerId(1L)).willReturn(patients);

        List<PatientDTO> result = patientService.getPatientsByGeneralPractitioner(1L);

        assertThat(result).isNotNull();
        assertEquals(1, result.size());
        assertEquals("Petar Georgiev", result.get(0).getName());
    }
}
