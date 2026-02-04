package com.cscb869.medical_record.service.impl;

import com.cscb869.medical_record.data.entity.Doctor;
import com.cscb869.medical_record.data.repo.DoctorRepository;
import com.cscb869.medical_record.dto.CreateDoctorDTO;
import com.cscb869.medical_record.dto.DoctorDTO;
import com.cscb869.medical_record.exception.DoctorNotFoundException;
import com.cscb869.medical_record.util.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class DoctorServiceImplTest {

    @MockitoBean
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorServiceImpl doctorService;

    @Autowired
    private MapperUtil mapperUtil;

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
    }

    @Test
    void getDoctorByIdSuccess() {
        given(doctorRepository.findById(1L))
                .willReturn(Optional.of(doctor));

        DoctorDTO result = doctorService.getDoctorById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Dr. Ivan Petrov");
        assertThat(result.getSpecialty()).isEqualTo("General Medicine");
    }

    @Test
    void getDoctorByIdNotFound() {
        given(doctorRepository.findById(999L))
                .willReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class,
                () -> doctorService.getDoctorById(999L));
    }

    @Test
    void getAllDoctors() {
        Doctor doctor2 = Doctor.builder()
                .uniqueIdentifier("DOC-002")
                .name("Dr. Maria Ivanova")
                .specialty("Cardiology")
                .generalPractitioner(false)
                .build();
        doctor2.setId(2L);

        List<Doctor> doctors = Arrays.asList(doctor, doctor2);

        given(doctorRepository.findAll()).willReturn(doctors);

        List<DoctorDTO> result = doctorService.getAllDoctors();

        assertThat(result).isNotNull();
        assertEquals(2, result.size());
        assertEquals("Dr. Ivan Petrov", result.get(0).getName());
        assertEquals("Dr. Maria Ivanova", result.get(1).getName());
    }

    @Test
    void createDoctor() {
        CreateDoctorDTO createDTO = new CreateDoctorDTO();
        createDTO.setUniqueIdentifier("DOC-003");
        createDTO.setName("Dr. New Doctor");
        createDTO.setSpecialty("Surgery");
        createDTO.setGeneralPractitioner(false);

        Doctor newDoctor = Doctor.builder()
                .uniqueIdentifier("DOC-003")
                .name("Dr. New Doctor")
                .specialty("Surgery")
                .generalPractitioner(false)
                .build();
        newDoctor.setId(3L);

        given(doctorRepository.save(any(Doctor.class))).willReturn(newDoctor);

        DoctorDTO result = doctorService.createDoctor(createDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Dr. New Doctor");
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    @Test
    void updateDoctorSuccess() {
        CreateDoctorDTO updateDTO = new CreateDoctorDTO();
        updateDTO.setUniqueIdentifier("DOC-001");
        updateDTO.setName("Dr. Ivan Petrov Updated");
        updateDTO.setSpecialty("Cardiology");
        updateDTO.setGeneralPractitioner(false);

        Doctor updatedDoctor = Doctor.builder()
                .uniqueIdentifier("DOC-001")
                .name("Dr. Ivan Petrov Updated")
                .specialty("Cardiology")
                .generalPractitioner(false)
                .build();
        updatedDoctor.setId(1L);

        given(doctorRepository.findById(1L)).willReturn(Optional.of(doctor));
        given(doctorRepository.save(any(Doctor.class))).willReturn(updatedDoctor);

        DoctorDTO result = doctorService.updateDoctor(1L, updateDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Dr. Ivan Petrov Updated");
        assertThat(result.getSpecialty()).isEqualTo("Cardiology");
    }

    @Test
    void updateDoctorNotFound() {
        CreateDoctorDTO updateDTO = new CreateDoctorDTO();
        updateDTO.setName("Updated Name");

        given(doctorRepository.findById(999L)).willReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class,
                () -> doctorService.updateDoctor(999L, updateDTO));
    }

    @Test
    void deleteDoctorSuccess() {
        given(doctorRepository.existsById(1L)).willReturn(true);
        doNothing().when(doctorRepository).deleteById(1L);

        assertDoesNotThrow(() -> doctorService.deleteDoctor(1L));
        verify(doctorRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteDoctorNotFound() {
        given(doctorRepository.existsById(999L)).willReturn(false);

        assertThrows(DoctorNotFoundException.class,
                () -> doctorService.deleteDoctor(999L));
    }

    @Test
    void getGeneralPractitioners() {
        Doctor gp = Doctor.builder()
                .uniqueIdentifier("DOC-001")
                .name("Dr. Ivan Petrov")
                .specialty("General Medicine")
                .generalPractitioner(true)
                .build();
        gp.setId(1L);

        List<Doctor> gps = Arrays.asList(gp);

        given(doctorRepository.findByGeneralPractitionerTrue()).willReturn(gps);

        List<DoctorDTO> result = doctorService.getGeneralPractitioners();

        assertThat(result).isNotNull();
        assertEquals(1, result.size());
        assertTrue(result.get(0).isGeneralPractitioner());
    }
}
