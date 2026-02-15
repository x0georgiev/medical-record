package com.cscb869.medical_record.web.view.controller;

import com.cscb869.medical_record.dto.CreateSickLeaveDTO;
import com.cscb869.medical_record.dto.SickLeaveDTO;
import com.cscb869.medical_record.service.ExaminationService;
import com.cscb869.medical_record.service.SickLeaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sick-leaves")
public class SickLeaveViewController {

    private final SickLeaveService sickLeaveService;
    private final ExaminationService examinationService;

    @GetMapping
    public String getSickLeaves(Model model) {
        List<SickLeaveDTO> sickLeaves = sickLeaveService.getAllSickLeaves();
        model.addAttribute("sickLeaves", sickLeaves);
        return "/sick-leaves/sick-leaves";
    }

    @GetMapping("/create-sick-leave")
    public String showCreateSickLeaveForm(Model model) {
        model.addAttribute("sickLeave", new CreateSickLeaveDTO());
        model.addAttribute("examinations", examinationService.getAllExaminations());
        return "/sick-leaves/create-sick-leave";
    }

    @PostMapping("/create")
    public String createSickLeave(@Valid @ModelAttribute("sickLeave") CreateSickLeaveDTO sickLeave,
                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("examinations", examinationService.getAllExaminations());
            return "/sick-leaves/create-sick-leave";
        }
        sickLeaveService.createSickLeave(sickLeave);
        return "redirect:/sick-leaves";
    }

    @GetMapping("/delete/{id}")
    public String deleteSickLeave(@PathVariable Long id) {
        sickLeaveService.deleteSickLeave(id);
        return "redirect:/sick-leaves";
    }
}
