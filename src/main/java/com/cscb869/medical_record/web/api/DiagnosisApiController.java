package com.cscb869.medical_record.web.api;

import com.cscb869.medical_record.dto.CreateDiagnosisDTO;
import com.cscb869.medical_record.dto.DiagnosisDTO;
import com.cscb869.medical_record.service.DiagnosisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diagnoses")
@RequiredArgsConstructor
@CrossOrigin
public class DiagnosisApiController {

    private final DiagnosisService diagnosisService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DiagnosisDTO>> getAllDiagnoses() {
        return ResponseEntity.ok(diagnosisService.getAllDiagnoses());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DiagnosisDTO> getDiagnosisById(@PathVariable Long id) {
        return ResponseEntity.ok(diagnosisService.getDiagnosisById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DiagnosisDTO> createDiagnosis(@Valid @RequestBody CreateDiagnosisDTO createDiagnosisDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(diagnosisService.createDiagnosis(createDiagnosisDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DiagnosisDTO> updateDiagnosis(@PathVariable Long id, @Valid @RequestBody CreateDiagnosisDTO createDiagnosisDTO) {
        return ResponseEntity.ok(diagnosisService.updateDiagnosis(id, createDiagnosisDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDiagnosis(@PathVariable Long id) {
        diagnosisService.deleteDiagnosis(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/most-common")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<Object[]>> getMostCommonDiagnoses() {
        return ResponseEntity.ok(diagnosisService.getMostCommonDiagnoses());
    }
}
