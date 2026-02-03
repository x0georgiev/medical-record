package com.cscb869.medical_record.web.api;

import com.cscb869.medical_record.dto.CreateMedicineDTO;
import com.cscb869.medical_record.dto.MedicineDTO;
import com.cscb869.medical_record.service.MedicineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
@RequiredArgsConstructor
@CrossOrigin
public class MedicineApiController {

    private final MedicineService medicineService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MedicineDTO>> getAllMedicines() {
        return ResponseEntity.ok(medicineService.getAllMedicines());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MedicineDTO> getMedicineById(@PathVariable Long id) {
        return ResponseEntity.ok(medicineService.getMedicineById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MedicineDTO> createMedicine(@Valid @RequestBody CreateMedicineDTO createMedicineDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicineService.createMedicine(createMedicineDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MedicineDTO> updateMedicine(@PathVariable Long id, @Valid @RequestBody CreateMedicineDTO createMedicineDTO) {
        return ResponseEntity.ok(medicineService.updateMedicine(id, createMedicineDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MedicineDTO>> searchMedicines(@RequestParam String name) {
        return ResponseEntity.ok(medicineService.searchMedicines(name));
    }

    @GetMapping("/most-prescribed")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<Object[]>> getMostPrescribedMedicines() {
        return ResponseEntity.ok(medicineService.getMostPrescribedMedicines());
    }
}
