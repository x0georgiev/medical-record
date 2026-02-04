package com.cscb869.medical_record.controller;

import com.cscb869.medical_record.dto.CreatePatientDTO;
import com.cscb869.medical_record.dto.PatientDTO;
import com.cscb869.medical_record.service.PatientService;
import com.cscb869.medical_record.web.api.PatientApiController;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientApiController.class)
class PatientApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PatientService patientService;

    @Test
    @WithMockUser
    void getAllPatientsTest() throws Exception {
        PatientDTO patient1 = new PatientDTO();
        patient1.setId(1L);
        patient1.setName("Petar Georgiev");
        patient1.setEgn("8501011234");
        patient1.setHealthInsurancePaid(true);

        PatientDTO patient2 = new PatientDTO();
        patient2.setId(2L);
        patient2.setName("Stefka Marinova");
        patient2.setEgn("9003152345");
        patient2.setHealthInsurancePaid(true);

        List<PatientDTO> patients = Arrays.asList(patient1, patient2);

        given(patientService.getAllPatients()).willReturn(patients);

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Petar Georgiev")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Stefka Marinova")))
                .andDo(print());
    }

    @Test
    @WithMockUser
    void getPatientByIdTest() throws Exception {
        PatientDTO patient = new PatientDTO();
        patient.setId(1L);
        patient.setName("Petar Georgiev");
        patient.setEgn("8501011234");
        patient.setHealthInsurancePaid(true);
        patient.setLastInsurancePaymentDate(LocalDate.of(2025, 12, 15));
        patient.setGeneralPractitionerId(1L);
        patient.setGeneralPractitionerName("Dr. Ivan Petrov");

        given(patientService.getPatientById(1L)).willReturn(patient);

        mockMvc.perform(get("/api/patients/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Petar Georgiev")))
                .andExpect(jsonPath("$.egn", is("8501011234")))
                .andExpect(jsonPath("$.healthInsurancePaid", is(true)))
                .andDo(print());
    }

    @Test
    @WithMockUser
    void createPatientTest() throws Exception {
        CreatePatientDTO createDTO = new CreatePatientDTO();
        createDTO.setName("New Patient");
        createDTO.setEgn("9901011234");
        createDTO.setHealthInsurancePaid(true);
        createDTO.setLastInsurancePaymentDate(LocalDate.of(2026, 1, 15));
        createDTO.setGeneralPractitionerId(1L);

        PatientDTO createdPatient = new PatientDTO();
        createdPatient.setId(10L);
        createdPatient.setName("New Patient");
        createdPatient.setEgn("9901011234");
        createdPatient.setHealthInsurancePaid(true);

        given(patientService.createPatient(any(CreatePatientDTO.class))).willReturn(createdPatient);

        mockMvc.perform(post("/api/patients")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.name", is("New Patient")))
                .andDo(print());
    }

    @Test
    @WithMockUser
    void getPatientsByGeneralPractitionerTest() throws Exception {
        PatientDTO patient1 = new PatientDTO();
        patient1.setId(1L);
        patient1.setName("Petar Georgiev");
        patient1.setGeneralPractitionerId(1L);

        PatientDTO patient2 = new PatientDTO();
        patient2.setId(2L);
        patient2.setName("Stefka Marinova");
        patient2.setGeneralPractitionerId(1L);

        List<PatientDTO> patients = Arrays.asList(patient1, patient2);

        given(patientService.getPatientsByGeneralPractitioner(1L)).willReturn(patients);

        mockMvc.perform(get("/api/patients/gp/{doctorId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].generalPractitionerId", is(1)))
                .andExpect(jsonPath("$[1].generalPractitionerId", is(1)))
                .andDo(print());
    }

    @Test
    @WithMockUser
    void getPatientsByDiagnosisTest() throws Exception {
        PatientDTO patient1 = new PatientDTO();
        patient1.setId(1L);
        patient1.setName("Petar Georgiev");

        List<PatientDTO> patients = Arrays.asList(patient1);

        given(patientService.getPatientsByDiagnosis(1L)).willReturn(patients);

        mockMvc.perform(get("/api/patients/diagnosis/{diagnosisId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Petar Georgiev")))
                .andDo(print());
    }
}
