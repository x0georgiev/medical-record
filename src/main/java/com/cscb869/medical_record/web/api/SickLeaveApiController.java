package com.cscb869.medical_record.web.api;

import com.cscb869.medical_record.dto.CreateSickLeaveDTO;
import com.cscb869.medical_record.dto.SickLeaveDTO;
import com.cscb869.medical_record.service.SickLeaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sick-leaves")
@RequiredArgsConstructor
@CrossOrigin
public class SickLeaveApiController {

    private final SickLeaveService sickLeaveService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<SickLeaveDTO>> getAllSickLeaves() {
        return ResponseEntity.ok(sickLeaveService.getAllSickLeaves());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<SickLeaveDTO> getSickLeaveById(@PathVariable Long id) {
        return ResponseEntity.ok(sickLeaveService.getSickLeaveById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<SickLeaveDTO> createSickLeave(@Valid @RequestBody CreateSickLeaveDTO createSickLeaveDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sickLeaveService.createSickLeave(createSickLeaveDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<SickLeaveDTO> updateSickLeave(@PathVariable Long id, @Valid @RequestBody CreateSickLeaveDTO createSickLeaveDTO) {
        return ResponseEntity.ok(sickLeaveService.updateSickLeave(id, createSickLeaveDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSickLeave(@PathVariable Long id) {
        sickLeaveService.deleteSickLeave(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/examination/{examinationId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<SickLeaveDTO> getSickLeaveByExamination(@PathVariable Long examinationId) {
        return ResponseEntity.ok(sickLeaveService.getSickLeaveByExamination(examinationId));
    }

    @GetMapping("/month-stats")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<Object[]> getMonthWithMostSickLeaves() {
        return ResponseEntity.ok(sickLeaveService.getMonthWithMostSickLeaves());
    }

    @GetMapping("/by-month")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<Object[]>> getSickLeavesByMonth() {
        return ResponseEntity.ok(sickLeaveService.getSickLeavesByMonth());
    }

    @GetMapping("/doctor-stats")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<Object[]>> getDoctorsWithMostSickLeaves() {
        return ResponseEntity.ok(sickLeaveService.getDoctorsWithMostSickLeaves());
    }
}
