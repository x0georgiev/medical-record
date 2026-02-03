package com.cscb869.medical_record.web.api;

import com.cscb869.medical_record.dto.CreateExaminationDTO;
import com.cscb869.medical_record.dto.ExaminationDTO;
import com.cscb869.medical_record.service.ExaminationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/examinations")
@RequiredArgsConstructor
@CrossOrigin
public class ExaminationApiController {

    private final ExaminationService examinationService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<ExaminationDTO>> getAllExaminations() {
        return ResponseEntity.ok(examinationService.getAllExaminations());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<ExaminationDTO> getExaminationById(@PathVariable Long id) {
        return ResponseEntity.ok(examinationService.getExaminationById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<ExaminationDTO> createExamination(@Valid @RequestBody CreateExaminationDTO createExaminationDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(examinationService.createExamination(createExaminationDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<ExaminationDTO> updateExamination(@PathVariable Long id, @Valid @RequestBody CreateExaminationDTO createExaminationDTO) {
        return ResponseEntity.ok(examinationService.updateExamination(id, createExaminationDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteExamination(@PathVariable Long id) {
        examinationService.deleteExamination(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<ExaminationDTO>> getExaminationsByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(examinationService.getExaminationsByPatient(patientId));
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<ExaminationDTO>> getExaminationsByDoctor(@PathVariable Long doctorId) {
        return ResponseEntity.ok(examinationService.getExaminationsByDoctor(doctorId));
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<ExaminationDTO>> getExaminationsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(examinationService.getExaminationsByDateRange(startDate, endDate));
    }

    @GetMapping("/doctor/{doctorId}/date-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<ExaminationDTO>> getExaminationsByDoctorAndDateRange(
            @PathVariable Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(examinationService.getExaminationsByDoctorAndDateRange(doctorId, startDate, endDate));
    }
}
