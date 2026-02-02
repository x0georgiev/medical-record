package com.cscb869.medical_record.service.impl;

import com.cscb869.medical_record.data.entity.Doctor;
import com.cscb869.medical_record.data.repo.DoctorRepository;
import com.cscb869.medical_record.dto.CreateDoctorDTO;
import com.cscb869.medical_record.dto.DoctorDTO;
import com.cscb869.medical_record.exception.DoctorNotFoundException;
import com.cscb869.medical_record.service.DoctorService;
import com.cscb869.medical_record.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final MapperUtil mapperUtil;

    @Override
    @Transactional(readOnly = true)
    public List<DoctorDTO> getAllDoctors() {
        return mapperUtil.mapList(doctorRepository.findAll(), DoctorDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id: " + id));
        return mapperUtil.getModelMapper().map(doctor, DoctorDTO.class);
    }

    @Override
    public DoctorDTO createDoctor(CreateDoctorDTO createDoctorDTO) {
        Doctor doctor = mapperUtil.getModelMapper().map(createDoctorDTO, Doctor.class);
        Doctor savedDoctor = doctorRepository.save(doctor);
        return mapperUtil.getModelMapper().map(savedDoctor, DoctorDTO.class);
    }

    @Override
    public DoctorDTO updateDoctor(Long id, CreateDoctorDTO createDoctorDTO) {
        return doctorRepository.findById(id)
                .map(doctor -> {
                    doctor.setUniqueIdentifier(createDoctorDTO.getUniqueIdentifier());
                    doctor.setName(createDoctorDTO.getName());
                    doctor.setSpecialty(createDoctorDTO.getSpecialty());
                    doctor.setGeneralPractitioner(createDoctorDTO.isGeneralPractitioner());
                    return mapperUtil.getModelMapper().map(doctorRepository.save(doctor), DoctorDTO.class);
                }).orElseThrow(() -> new DoctorNotFoundException("Doctor not found with id: " + id));
    }

    @Override
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new DoctorNotFoundException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorDTO> getGeneralPractitioners() {
        return mapperUtil.mapList(doctorRepository.findByIsGeneralPractitionerTrue(), DoctorDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getVisitCountPerDoctor() {
        return doctorRepository.getVisitCountPerDoctor();
    }
}
