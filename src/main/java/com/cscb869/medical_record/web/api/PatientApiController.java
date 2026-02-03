package com.cscb869.medical_record.web.api;

import com.cscb869.medical_record.dto.CreatePatientDTO;
import com.cscb869.medical_record.dto.PatientDTO;
import com.cscb869.medical_record.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@CrossOrigin
public class PatientApiController {

    private final PatientService patientService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody CreatePatientDTO createPatientDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.createPatient(createPatientDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @Valid @RequestBody CreatePatientDTO createPatientDTO) {
        return ResponseEntity.ok(patientService.updatePatient(id, createPatientDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/gp/{doctorId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<PatientDTO>> getPatientsByGeneralPractitioner(@PathVariable Long doctorId) {
        return ResponseEntity.ok(patientService.getPatientsByGeneralPractitioner(doctorId));
    }

    @GetMapping("/diagnosis/{diagnosisId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<PatientDTO>> getPatientsByDiagnosis(@PathVariable Long diagnosisId) {
        return ResponseEntity.ok(patientService.getPatientsByDiagnosis(diagnosisId));
    }

    @GetMapping("/gp-count")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<List<Object[]>> getPatientCountPerGeneralPractitioner() {
        return ResponseEntity.ok(patientService.getPatientCountPerGeneralPractitioner());
    }
}
