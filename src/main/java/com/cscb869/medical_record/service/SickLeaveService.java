package com.cscb869.medical_record.service;

import com.cscb869.medical_record.dto.CreateSickLeaveDTO;
import com.cscb869.medical_record.dto.SickLeaveDTO;

import java.util.List;

public interface SickLeaveService {

    List<SickLeaveDTO> getAllSickLeaves();

    SickLeaveDTO getSickLeaveById(Long id);

    SickLeaveDTO createSickLeave(CreateSickLeaveDTO createSickLeaveDTO);

    SickLeaveDTO updateSickLeave(Long id, CreateSickLeaveDTO createSickLeaveDTO);

    void deleteSickLeave(Long id);

    SickLeaveDTO getSickLeaveByExamination(Long examinationId);

    Object[] getMonthWithMostSickLeaves();

    List<Object[]> getSickLeavesByMonth();

    List<Object[]> getDoctorsWithMostSickLeaves();
}
