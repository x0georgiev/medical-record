package com.cscb869.medical_record.service.impl;

import com.cscb869.medical_record.data.entity.Examination;
import com.cscb869.medical_record.data.entity.SickLeave;
import com.cscb869.medical_record.data.repo.ExaminationRepository;
import com.cscb869.medical_record.data.repo.SickLeaveRepository;
import com.cscb869.medical_record.dto.CreateSickLeaveDTO;
import com.cscb869.medical_record.dto.SickLeaveDTO;
import com.cscb869.medical_record.exception.ExaminationNotFoundException;
import com.cscb869.medical_record.exception.SickLeaveNotFoundException;
import com.cscb869.medical_record.service.SickLeaveService;
import com.cscb869.medical_record.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SickLeaveServiceImpl implements SickLeaveService {

    private final SickLeaveRepository sickLeaveRepository;
    private final ExaminationRepository examinationRepository;
    private final MapperUtil mapperUtil;

    @Override
    @Transactional(readOnly = true)
    public List<SickLeaveDTO> getAllSickLeaves() {
        return mapperUtil.mapList(sickLeaveRepository.findAll(), SickLeaveDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public SickLeaveDTO getSickLeaveById(Long id) {
        SickLeave sickLeave = sickLeaveRepository.findById(id)
                .orElseThrow(() -> new SickLeaveNotFoundException("Sick leave not found with id: " + id));
        return mapperUtil.getModelMapper().map(sickLeave, SickLeaveDTO.class);
    }

    @Override
    public SickLeaveDTO createSickLeave(CreateSickLeaveDTO createSickLeaveDTO) {
        Examination examination = examinationRepository.findById(createSickLeaveDTO.getExaminationId())
                .orElseThrow(() -> new ExaminationNotFoundException("Examination not found"));
        
        SickLeave sickLeave = mapperUtil.getModelMapper().map(createSickLeaveDTO, SickLeave.class);
        sickLeave.setExamination(examination);
        
        SickLeave savedSickLeave = sickLeaveRepository.save(sickLeave);
        return mapperUtil.getModelMapper().map(savedSickLeave, SickLeaveDTO.class);
    }

    @Override
    public SickLeaveDTO updateSickLeave(Long id, CreateSickLeaveDTO createSickLeaveDTO) {
        Examination examination = examinationRepository.findById(createSickLeaveDTO.getExaminationId())
                .orElseThrow(() -> new ExaminationNotFoundException("Examination not found"));
        
        return sickLeaveRepository.findById(id)
                .map(sickLeave -> {
                    sickLeave.setExamination(examination);
                    sickLeave.setStartDate(createSickLeaveDTO.getStartDate());
                    sickLeave.setDurationDays(createSickLeaveDTO.getDurationDays());
                    sickLeave.setIssueDate(createSickLeaveDTO.getIssueDate());
                    return mapperUtil.getModelMapper().map(sickLeaveRepository.save(sickLeave), SickLeaveDTO.class);
                }).orElseThrow(() -> new SickLeaveNotFoundException("Sick leave not found with id: " + id));
    }

    @Override
    public void deleteSickLeave(Long id) {
        if (!sickLeaveRepository.existsById(id)) {
            throw new SickLeaveNotFoundException("Sick leave not found with id: " + id);
        }
        sickLeaveRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public SickLeaveDTO getSickLeaveByExamination(Long examinationId) {
        SickLeave sickLeave = sickLeaveRepository.findByExaminationId(examinationId);
        if (sickLeave == null) {
            return null;
        }
        return mapperUtil.getModelMapper().map(sickLeave, SickLeaveDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Object[] getMonthWithMostSickLeaves() {
        return sickLeaveRepository.getMonthWithMostSickLeaves();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getSickLeavesByMonth() {
        return sickLeaveRepository.getSickLeavesByMonth();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getDoctorsWithMostSickLeaves() {
        return sickLeaveRepository.findAll().stream()
                .collect(Collectors.groupingBy(sl -> sl.getExamination().getDoctor()))
                .entrySet().stream()
                .map(entry -> new Object[]{
                    entry.getKey().getId(),
                    entry.getKey().getName(),
                    entry.getValue().size()
                })
                .collect(Collectors.toList());
    }
}
