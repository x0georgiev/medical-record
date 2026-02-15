package com.cscb869.medical_record.web.view.controller;

import com.cscb869.medical_record.dto.CreateDiagnosisDTO;
import com.cscb869.medical_record.dto.DiagnosisDTO;
import com.cscb869.medical_record.service.DiagnosisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/diagnoses")
public class DiagnosisViewController {

    private final DiagnosisService diagnosisService;

    @GetMapping
    public String getDiagnoses(Model model) {
        List<DiagnosisDTO> diagnoses = diagnosisService.getAllDiagnoses();
        model.addAttribute("diagnoses", diagnoses);
        return "/diagnoses/diagnoses";
    }

    @GetMapping("/create-diagnosis")
    public String showCreateDiagnosisForm(Model model) {
        model.addAttribute("diagnosis", new CreateDiagnosisDTO());
        return "/diagnoses/create-diagnosis";
    }

    @PostMapping("/create")
    public String createDiagnosis(@Valid @ModelAttribute("diagnosis") CreateDiagnosisDTO diagnosis,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/diagnoses/create-diagnosis";
        }
        diagnosisService.createDiagnosis(diagnosis);
        return "redirect:/diagnoses";
    }

    @GetMapping("/edit-diagnosis/{id}")
    public String showEditDiagnosisForm(Model model, @PathVariable Long id) {
        DiagnosisDTO diagnosisDTO = diagnosisService.getDiagnosisById(id);
        CreateDiagnosisDTO editDTO = new CreateDiagnosisDTO();
        editDTO.setCode(diagnosisDTO.getCode());
        editDTO.setName(diagnosisDTO.getName());
        editDTO.setDescription(diagnosisDTO.getDescription());
        model.addAttribute("diagnosis", editDTO);
        model.addAttribute("diagnosisId", id);
        return "/diagnoses/edit-diagnosis";
    }

    @PostMapping("/update/{id}")
    public String updateDiagnosis(@PathVariable Long id,
                                  @Valid @ModelAttribute("diagnosis") CreateDiagnosisDTO diagnosis,
                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("diagnosisId", id);
            return "/diagnoses/edit-diagnosis";
        }
        diagnosisService.updateDiagnosis(id, diagnosis);
        return "redirect:/diagnoses";
    }

    @GetMapping("/delete/{id}")
    public String deleteDiagnosis(@PathVariable Long id) {
        diagnosisService.deleteDiagnosis(id);
        return "redirect:/diagnoses";
    }
}
