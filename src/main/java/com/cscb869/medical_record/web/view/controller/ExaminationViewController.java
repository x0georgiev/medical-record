package com.cscb869.medical_record.web.view.controller;

import com.cscb869.medical_record.dto.CreateExaminationDTO;
import com.cscb869.medical_record.dto.ExaminationDTO;
import com.cscb869.medical_record.service.DiagnosisService;
import com.cscb869.medical_record.service.DoctorService;
import com.cscb869.medical_record.service.ExaminationService;
import com.cscb869.medical_record.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/examinations")
public class ExaminationViewController {

    private final ExaminationService examinationService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final DiagnosisService diagnosisService;

    @GetMapping
    public String getExaminations(Model model) {
        List<ExaminationDTO> examinations = examinationService.getAllExaminations();
        model.addAttribute("examinations", examinations);
        return "/examinations/examinations";
    }

    @GetMapping("/create-examination")
    public String showCreateExaminationForm(Model model) {
        model.addAttribute("examination", new CreateExaminationDTO());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("diagnoses", diagnosisService.getAllDiagnoses());
        return "/examinations/create-examination";
    }

    @PostMapping("/create")
    public String createExamination(@Valid @ModelAttribute("examination") CreateExaminationDTO examination,
                                    BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doctors", doctorService.getAllDoctors());
            model.addAttribute("patients", patientService.getAllPatients());
            model.addAttribute("diagnoses", diagnosisService.getAllDiagnoses());
            return "/examinations/create-examination";
        }
        examinationService.createExamination(examination);
        return "redirect:/examinations";
    }

    @GetMapping("/delete/{id}")
    public String deleteExamination(@PathVariable Long id) {
        examinationService.deleteExamination(id);
        return "redirect:/examinations";
    }
}
