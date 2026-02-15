package com.cscb869.medical_record.web.view.controller;

import com.cscb869.medical_record.dto.CreateDoctorDTO;
import com.cscb869.medical_record.dto.DoctorDTO;
import com.cscb869.medical_record.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorViewController {

    private final DoctorService doctorService;

    @GetMapping
    public String getDoctors(Model model) {
        List<DoctorDTO> doctors = doctorService.getAllDoctors();
        model.addAttribute("doctors", doctors);
        return "/doctors/doctors";
    }

    @GetMapping("/create-doctor")
    public String showCreateDoctorForm(Model model) {
        model.addAttribute("doctor", new CreateDoctorDTO());
        return "/doctors/create-doctor";
    }

    @PostMapping("/create")
    public String createDoctor(@Valid @ModelAttribute("doctor") CreateDoctorDTO doctor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/doctors/create-doctor";
        }
        doctorService.createDoctor(doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/edit-doctor/{id}")
    public String showEditDoctorForm(Model model, @PathVariable Long id) {
        DoctorDTO doctorDTO = doctorService.getDoctorById(id);
        CreateDoctorDTO editDTO = new CreateDoctorDTO();
        editDTO.setUniqueIdentifier(doctorDTO.getUniqueIdentifier());
        editDTO.setName(doctorDTO.getName());
        editDTO.setSpecialty(doctorDTO.getSpecialty());
        editDTO.setGeneralPractitioner(doctorDTO.isGeneralPractitioner());
        model.addAttribute("doctor", editDTO);
        model.addAttribute("doctorId", id);
        return "/doctors/edit-doctor";
    }

    @PostMapping("/update/{id}")
    public String updateDoctor(@PathVariable Long id,
                               @Valid @ModelAttribute("doctor") CreateDoctorDTO doctor,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doctorId", id);
            return "/doctors/edit-doctor";
        }
        doctorService.updateDoctor(id, doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctors";
    }
}
