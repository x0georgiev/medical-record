package com.cscb869.medical_record.web.view.controller;

import com.cscb869.medical_record.dto.CreateMedicineDTO;
import com.cscb869.medical_record.dto.MedicineDTO;
import com.cscb869.medical_record.service.MedicineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/medicines")
public class MedicineViewController {

    private final MedicineService medicineService;

    @GetMapping
    public String getMedicines(Model model) {
        List<MedicineDTO> medicines = medicineService.getAllMedicines();
        model.addAttribute("medicines", medicines);
        return "/medicines/medicines";
    }

    @GetMapping("/create-medicine")
    public String showCreateMedicineForm(Model model) {
        model.addAttribute("medicine", new CreateMedicineDTO());
        return "/medicines/create-medicine";
    }

    @PostMapping("/create")
    public String createMedicine(@Valid @ModelAttribute("medicine") CreateMedicineDTO medicine,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/medicines/create-medicine";
        }
        medicineService.createMedicine(medicine);
        return "redirect:/medicines";
    }

    @GetMapping("/edit-medicine/{id}")
    public String showEditMedicineForm(Model model, @PathVariable Long id) {
        MedicineDTO medicineDTO = medicineService.getMedicineById(id);
        CreateMedicineDTO editDTO = new CreateMedicineDTO();
        editDTO.setName(medicineDTO.getName());
        editDTO.setDescription(medicineDTO.getDescription());
        editDTO.setManufacturer(medicineDTO.getManufacturer());
        model.addAttribute("medicine", editDTO);
        model.addAttribute("medicineId", id);
        return "/medicines/edit-medicine";
    }

    @PostMapping("/update/{id}")
    public String updateMedicine(@PathVariable Long id,
                                 @Valid @ModelAttribute("medicine") CreateMedicineDTO medicine,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("medicineId", id);
            return "/medicines/edit-medicine";
        }
        medicineService.updateMedicine(id, medicine);
        return "redirect:/medicines";
    }

    @GetMapping("/delete/{id}")
    public String deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return "redirect:/medicines";
    }
}
