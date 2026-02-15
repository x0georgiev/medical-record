package com.cscb869.medical_record.web.view.controller;

import com.cscb869.medical_record.dto.CreatePatientDTO;
import com.cscb869.medical_record.dto.DoctorDTO;
import com.cscb869.medical_record.dto.PatientDTO;
import com.cscb869.medical_record.service.DoctorService;
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
@RequestMapping("/patients")
public class PatientViewController {

    private final PatientService patientService;
    private final DoctorService doctorService;

    @GetMapping
    public String getPatients(Model model) {
        List<PatientDTO> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        return "/patients/patients";
    }

    @GetMapping("/create-patient")
    public String showCreatePatientForm(Model model) {
        model.addAttribute("patient", new CreatePatientDTO());
        model.addAttribute("doctors", doctorService.getGeneralPractitioners());
        return "/patients/create-patient";
    }

    @PostMapping("/create")
    public String createPatient(@Valid @ModelAttribute("patient") CreatePatientDTO patient,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doctors", doctorService.getGeneralPractitioners());
            return "/patients/create-patient";
        }
        patientService.createPatient(patient);
        return "redirect:/patients";
    }

    @GetMapping("/edit-patient/{id}")
    public String showEditPatientForm(Model model, @PathVariable Long id) {
        PatientDTO patientDTO = patientService.getPatientById(id);
        CreatePatientDTO editDTO = new CreatePatientDTO();
        editDTO.setName(patientDTO.getName());
        editDTO.setEgn(patientDTO.getEgn());
        editDTO.setHealthInsurancePaid(patientDTO.isHealthInsurancePaid());
        editDTO.setLastInsurancePaymentDate(patientDTO.getLastInsurancePaymentDate());
        editDTO.setGeneralPractitionerId(patientDTO.getGeneralPractitionerId());
        model.addAttribute("patient", editDTO);
        model.addAttribute("patientId", id);
        model.addAttribute("doctors", doctorService.getGeneralPractitioners());
        return "/patients/edit-patient";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable Long id,
                                @Valid @ModelAttribute("patient") CreatePatientDTO patient,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("patientId", id);
            model.addAttribute("doctors", doctorService.getGeneralPractitioners());
            return "/patients/edit-patient";
        }
        patientService.updatePatient(id, patient);
        return "redirect:/patients";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "redirect:/patients";
    }
}
