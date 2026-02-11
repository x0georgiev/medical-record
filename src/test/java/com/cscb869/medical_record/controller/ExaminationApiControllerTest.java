package com.cscb869.medical_record.controller;

import com.cscb869.medical_record.dto.ExaminationDTO;
import com.cscb869.medical_record.service.ExaminationService;
import com.cscb869.medical_record.web.api.ExaminationApiController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExaminationApiController.class)
class ExaminationApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExaminationService examinationService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllExaminationsTest() throws Exception {
        ExaminationDTO exam1 = new ExaminationDTO();
        exam1.setId(1L);
        exam1.setPatientId(1L);
        exam1.setPatientName("Petar Georgiev");
        exam1.setDoctorId(1L);
        exam1.setDoctorName("Dr. Ivan Petrov");
        exam1.setExaminationDate(LocalDate.of(2025, 11, 15));
        exam1.setDiagnosisId(1L);
        exam1.setDiagnosisName("Acute upper respiratory infection");

        ExaminationDTO exam2 = new ExaminationDTO();
        exam2.setId(2L);
        exam2.setPatientId(1L);
        exam2.setPatientName("Petar Georgiev");
        exam2.setDoctorId(2L);
        exam2.setDoctorName("Dr. Maria Ivanova");
        exam2.setExaminationDate(LocalDate.of(2025, 12, 1));
        exam2.setDiagnosisId(2L);
        exam2.setDiagnosisName("Essential hypertension");

        List<ExaminationDTO> examinations = Arrays.asList(exam1, exam2);

        given(examinationService.getAllExaminations()).willReturn(examinations);

        mockMvc.perform(get("/api/examinations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].patientName", is("Petar Georgiev")))
                .andExpect(jsonPath("$[0].doctorName", is("Dr. Ivan Petrov")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].diagnosisName", is("Essential hypertension")))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getExaminationByIdTest() throws Exception {
        ExaminationDTO exam = new ExaminationDTO();
        exam.setId(1L);
        exam.setPatientId(1L);
        exam.setPatientName("Petar Georgiev");
        exam.setDoctorId(1L);
        exam.setDoctorName("Dr. Ivan Petrov");
        exam.setExaminationDate(LocalDate.of(2025, 11, 15));
        exam.setDiagnosisId(1L);
        exam.setDiagnosisName("Acute upper respiratory infection");

        given(examinationService.getExaminationById(1L)).willReturn(exam);

        mockMvc.perform(get("/api/examinations/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.patientName", is("Petar Georgiev")))
                .andExpect(jsonPath("$.doctorName", is("Dr. Ivan Petrov")))
                .andExpect(jsonPath("$.examinationDate", is("2025-11-15")))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getExaminationsByPatientTest() throws Exception {
        ExaminationDTO exam1 = new ExaminationDTO();
        exam1.setId(1L);
        exam1.setPatientId(1L);
        exam1.setPatientName("Petar Georgiev");
        exam1.setExaminationDate(LocalDate.of(2025, 11, 15));

        ExaminationDTO exam2 = new ExaminationDTO();
        exam2.setId(2L);
        exam2.setPatientId(1L);
        exam2.setPatientName("Petar Georgiev");
        exam2.setExaminationDate(LocalDate.of(2025, 12, 1));

        List<ExaminationDTO> examinations = Arrays.asList(exam1, exam2);

        given(examinationService.getExaminationsByPatient(1L)).willReturn(examinations);

        mockMvc.perform(get("/api/examinations/patient/{patientId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].patientId", is(1)))
                .andExpect(jsonPath("$[1].patientId", is(1)))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getExaminationsByDateRangeTest() throws Exception {
        ExaminationDTO exam1 = new ExaminationDTO();
        exam1.setId(1L);
        exam1.setExaminationDate(LocalDate.of(2025, 11, 15));

        ExaminationDTO exam2 = new ExaminationDTO();
        exam2.setId(4L);
        exam2.setExaminationDate(LocalDate.of(2025, 10, 20));

        List<ExaminationDTO> examinations = Arrays.asList(exam1, exam2);

        given(examinationService.getExaminationsByDateRange(
                LocalDate.of(2025, 10, 1),
                LocalDate.of(2025, 11, 30)
        )).willReturn(examinations);

        mockMvc.perform(get("/api/examinations/date-range")
                        .param("startDate", "2025-10-01")
                        .param("endDate", "2025-11-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(4)))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getExaminationsByDoctorTest() throws Exception {
        ExaminationDTO exam1 = new ExaminationDTO();
        exam1.setId(1L);
        exam1.setDoctorId(1L);
        exam1.setDoctorName("Dr. Ivan Petrov");

        List<ExaminationDTO> examinations = Arrays.asList(exam1);

        given(examinationService.getExaminationsByDoctor(1L)).willReturn(examinations);

        mockMvc.perform(get("/api/examinations/doctor/{doctorId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].doctorId", is(1)))
                .andDo(print());
    }
}
