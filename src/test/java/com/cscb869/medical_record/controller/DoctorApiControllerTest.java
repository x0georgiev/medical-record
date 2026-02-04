package com.cscb869.medical_record.controller;

import com.cscb869.medical_record.dto.CreateDoctorDTO;
import com.cscb869.medical_record.dto.DoctorDTO;
import com.cscb869.medical_record.service.DoctorService;
import com.cscb869.medical_record.web.api.DoctorApiController;
import tools.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DoctorApiController.class)
class DoctorApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final JsonMapper jsonMapper = JsonMapper.builder().build();

    @MockitoBean
    private DoctorService doctorService;

    @Test
    @WithMockUser
    void getAllDoctorsTest() throws Exception {
        DoctorDTO doctor1 = new DoctorDTO();
        doctor1.setId(1L);
        doctor1.setUniqueIdentifier("DOC-001");
        doctor1.setName("Dr. Ivan Petrov");
        doctor1.setSpecialty("General Medicine");
        doctor1.setGeneralPractitioner(true);

        DoctorDTO doctor2 = new DoctorDTO();
        doctor2.setId(2L);
        doctor2.setUniqueIdentifier("DOC-002");
        doctor2.setName("Dr. Maria Ivanova");
        doctor2.setSpecialty("Cardiology");
        doctor2.setGeneralPractitioner(false);

        List<DoctorDTO> doctors = Arrays.asList(doctor1, doctor2);

        given(doctorService.getAllDoctors()).willReturn(doctors);

        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Dr. Ivan Petrov")))
                .andExpect(jsonPath("$[0].specialty", is("General Medicine")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Dr. Maria Ivanova")))
                .andDo(print());
    }

    @Test
    @WithMockUser
    void getDoctorByIdTest() throws Exception {
        DoctorDTO doctor = new DoctorDTO();
        doctor.setId(1L);
        doctor.setUniqueIdentifier("DOC-001");
        doctor.setName("Dr. Ivan Petrov");
        doctor.setSpecialty("General Medicine");
        doctor.setGeneralPractitioner(true);

        given(doctorService.getDoctorById(1L)).willReturn(doctor);

        mockMvc.perform(get("/api/doctors/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.uniqueIdentifier", is("DOC-001")))
                .andExpect(jsonPath("$.name", is("Dr. Ivan Petrov")))
                .andExpect(jsonPath("$.specialty", is("General Medicine")))
                .andExpect(jsonPath("$.generalPractitioner", is(true)))
                .andDo(print());
    }

    @Test
    @WithMockUser
    void createDoctorTest() throws Exception {
        CreateDoctorDTO createDTO = new CreateDoctorDTO();
        createDTO.setUniqueIdentifier("DOC-003");
        createDTO.setName("Dr. Test Doctor");
        createDTO.setSpecialty("Surgery");
        createDTO.setGeneralPractitioner(false);

        DoctorDTO createdDoctor = new DoctorDTO();
        createdDoctor.setId(3L);
        createdDoctor.setUniqueIdentifier("DOC-003");
        createdDoctor.setName("Dr. Test Doctor");
        createdDoctor.setSpecialty("Surgery");
        createdDoctor.setGeneralPractitioner(false);

        given(doctorService.createDoctor(any(CreateDoctorDTO.class))).willReturn(createdDoctor);

        mockMvc.perform(post("/api/doctors")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is("Dr. Test Doctor")))
                .andDo(print());
    }

    @Test
    @WithMockUser
    void updateDoctorTest() throws Exception {
        CreateDoctorDTO updateDTO = new CreateDoctorDTO();
        updateDTO.setUniqueIdentifier("DOC-001");
        updateDTO.setName("Dr. Ivan Petrov Updated");
        updateDTO.setSpecialty("Cardiology");
        updateDTO.setGeneralPractitioner(false);

        DoctorDTO updatedDoctor = new DoctorDTO();
        updatedDoctor.setId(1L);
        updatedDoctor.setUniqueIdentifier("DOC-001");
        updatedDoctor.setName("Dr. Ivan Petrov Updated");
        updatedDoctor.setSpecialty("Cardiology");
        updatedDoctor.setGeneralPractitioner(false);

        given(doctorService.updateDoctor(eq(1L), any(CreateDoctorDTO.class))).willReturn(updatedDoctor);

        mockMvc.perform(put("/api/doctors/{id}", 1L)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Dr. Ivan Petrov Updated")))
                .andExpect(jsonPath("$.specialty", is("Cardiology")))
                .andDo(print());
    }

    @Test
    @WithMockUser
    void deleteDoctorTest() throws Exception {
        doNothing().when(doctorService).deleteDoctor(1L);

        mockMvc.perform(delete("/api/doctors/{id}", 1L)
                        .with(csrf()))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @WithMockUser
    void getGeneralPractitionersTest() throws Exception {
        DoctorDTO gp1 = new DoctorDTO();
        gp1.setId(1L);
        gp1.setName("Dr. Ivan Petrov");
        gp1.setGeneralPractitioner(true);

        DoctorDTO gp2 = new DoctorDTO();
        gp2.setId(3L);
        gp2.setName("Dr. Georgi Dimitrov");
        gp2.setGeneralPractitioner(true);

        List<DoctorDTO> gps = Arrays.asList(gp1, gp2);

        given(doctorService.getGeneralPractitioners()).willReturn(gps);

        mockMvc.perform(get("/api/doctors/general-practitioners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].generalPractitioner", is(true)))
                .andExpect(jsonPath("$[1].generalPractitioner", is(true)))
                .andDo(print());
    }
}
