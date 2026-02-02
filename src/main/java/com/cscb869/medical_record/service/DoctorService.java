package com.cscb869.medical_record.service;

import com.cscb869.medical_record.dto.CreateDoctorDTO;
import com.cscb869.medical_record.dto.DoctorDTO;

import java.util.List;

public interface DoctorService {

    List<DoctorDTO> getAllDoctors();

    DoctorDTO getDoctorById(Long id);

    DoctorDTO createDoctor(CreateDoctorDTO createDoctorDTO);

    DoctorDTO updateDoctor(Long id, CreateDoctorDTO createDoctorDTO);

    void deleteDoctor(Long id);

    List<DoctorDTO> getGeneralPractitioners();

    List<Object[]> getVisitCountPerDoctor();
}
