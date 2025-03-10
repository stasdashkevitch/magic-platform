package com.platform.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.school.dto.SchoolRequest;
import com.platform.school.dto.SchoolResponse;
import com.platform.school.dto.TimeRangeDto;
import com.platform.school.service.SchoolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SchoolController.class)
public class SchoolControllerTest {

    private final static String BASE_URL = "/api/v1/schools";

    private static SchoolResponse schoolResponse;
    private static SchoolRequest schoolRequest;
    private static SchoolRequest updateSchoolRequest;
    private static SchoolResponse updatedSchoolResponse;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SchoolService schoolService;

    @BeforeEach
    public void setUp() {
        schoolResponse = new SchoolResponse(
                1L,
                "СРЕДНЯЯ ШКОЛА №3 г. Иваново",
                "Брестская область",
                "г. Иваново",
                "ул. Советская, 26",
                "(01652) 9 50 82",
                "sch3@ivanovo.edu.by",
                "ГУО",
                1992,
                1000,
                9090,
                60,
                90,
                60,
                List.of("Библиотека"),
                new TimeRangeDto(
                        LocalTime.of(7, 0),
                        LocalTime.of(21, 0)
                ),
                new TimeRangeDto(
                        LocalTime.of(8, 0),
                        LocalTime.of(20, 0)
                )
        );

        schoolRequest = new SchoolRequest(
                "СРЕДНЯЯ ШКОЛА №3 г. Иваново",
                "Брестская область",
                "г. Иваново",
                "ул. Советская, 26",
                "(01652) 9 50 82",
                "sch3@ivanovo.edu.by",
                "ГУО",
                1992,
                1000,
                9090,
                60,
                90,
                60,
                List.of("Библиотека"),
                new TimeRangeDto(
                        LocalTime.of(7, 0),
                        LocalTime.of(21, 0)
                ),
                new TimeRangeDto(
                        LocalTime.of(8, 0),
                        LocalTime.of(20, 0)
                )
        );

        updateSchoolRequest = new SchoolRequest(
                null,
                null,
                null,
                null,
                "(01652) 9 50 83",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        updatedSchoolResponse = new SchoolResponse(
                1L,
                "СРЕДНЯЯ ШКОЛА №3 г. Иваново",
                "Брестская область",
                "г. Иваново",
                "ул. Советская, 26",
                updateSchoolRequest.phoneNumber(),
                "sch3@ivanovo.edu.by",
                "ГУО",
                1992,
                1000,
                9090,
                60,
                90,
                60,
                List.of("Библиотека"),
                new TimeRangeDto(
                        LocalTime.of(7, 0),
                        LocalTime.of(21, 0)
                ),
                new TimeRangeDto(
                        LocalTime.of(8, 0),
                        LocalTime.of(20, 0)
                )
        );
    }

    @Test
    public void shouldReturnJsonSchoolResponseById() throws Exception {
        given(schoolService.getSchoolById(anyLong())).willReturn(schoolResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL + "/{school-id}", 1)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(schoolResponse.id()))
                .andExpect(jsonPath("$.name").value(schoolResponse.name()))
                .andExpect(jsonPath("$.region").value(schoolResponse.region()))
                .andExpect(jsonPath("$.locality").value(schoolResponse.locality()))
                .andExpect(jsonPath("$.address").value(schoolResponse.address()))
                .andExpect(jsonPath("$.phoneNumber").value(schoolResponse.phoneNumber()))
                .andExpect(jsonPath("$.email").value(schoolResponse.email()))
                .andExpect(jsonPath("$.type").value(schoolResponse.type()))
                .andExpect(jsonPath("$.establishedYear").value(schoolResponse.establishedYear()))
                .andExpect(jsonPath("$.studentCapacity").value(schoolResponse.studentCapacity()))
                .andExpect(jsonPath("$.studentCount").value(schoolResponse.studentCount()))
                .andExpect(jsonPath("$.teacherCount").value(schoolResponse.teacherCount()))
                .andExpect(jsonPath("$.staffCount").value(schoolResponse.staffCount()))
                .andExpect(jsonPath("$.classroomCount").value(schoolResponse.classroomCount()))
                .andExpect(jsonPath("$.facilities", hasItem(schoolResponse.facilities().get(0))))
                .andExpect(jsonPath("$.workTime.start").value(schoolResponse.workTime().start().toString()))
                .andExpect(jsonPath("$.workTime.end").value(schoolResponse.workTime().end().toString()))
                .andExpect(jsonPath("$.schoolHours.start").value(schoolResponse.schoolHours().start().toString()))
                .andExpect(jsonPath("$.schoolHours.end").value(schoolResponse.schoolHours().end().toString()));

        verify(schoolService).getSchoolById(anyLong());
    }

    @Test
    public void shouldCreateSchoolAndReturnJsonSchoolResponse() throws Exception {
        var schoolRequestJson = objectMapper.writeValueAsString(schoolRequest);

        given(schoolService.createSchool(any(SchoolRequest.class))).willReturn(schoolResponse);

        mockMvc.perform(
                MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(schoolRequestJson)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(schoolResponse.id()))
                .andExpect(jsonPath("$.name").value(schoolRequest.name()))
                .andExpect(jsonPath("$.region").value(schoolRequest.region()))
                .andExpect(jsonPath("$.locality").value(schoolRequest.locality()))
                .andExpect(jsonPath("$.address").value(schoolRequest.address()))
                .andExpect(jsonPath("$.phoneNumber").value(schoolRequest.phoneNumber()))
                .andExpect(jsonPath("$.email").value(schoolRequest.email()))
                .andExpect(jsonPath("$.type").value(schoolRequest.type()))
                .andExpect(jsonPath("$.establishedYear").value(schoolRequest.establishedYear()))
                .andExpect(jsonPath("$.studentCapacity").value(schoolRequest.studentCapacity()))
                .andExpect(jsonPath("$.studentCount").value(schoolRequest.studentCount()))
                .andExpect(jsonPath("$.teacherCount").value(schoolRequest.teacherCount()))
                .andExpect(jsonPath("$.staffCount").value(schoolRequest.staffCount()))
                .andExpect(jsonPath("$.classroomCount").value(schoolRequest.classroomCount()))
                .andExpect(jsonPath("$.facilities", hasItem(schoolRequest.facilities().get(0))))
                .andExpect(jsonPath("$.workTime.start").value(schoolRequest.workTime().start().toString()))
                .andExpect(jsonPath("$.workTime.end").value(schoolRequest.workTime().end().toString()))
                .andExpect(jsonPath("$.schoolHours.start").value(schoolRequest.schoolHours().start().toString()))
                .andExpect(jsonPath("$.schoolHours.end").value(schoolRequest.schoolHours().end().toString()));

        verify(schoolService).createSchool(any(SchoolRequest.class));
    }

    @Test
    public void shouldDeleteSchoolByIdAndReturnJsonSchoolResponse() throws Exception {
        given(schoolService.deleteSchoolById(anyLong())).willReturn(schoolResponse);

        mockMvc.perform(
                MockMvcRequestBuilders.delete(BASE_URL + "/{school-id}", 1)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(schoolResponse.id()))
                .andExpect(jsonPath("$.name").value(schoolResponse.name()))
                .andExpect(jsonPath("$.region").value(schoolResponse.region()))
                .andExpect(jsonPath("$.locality").value(schoolResponse.locality()))
                .andExpect(jsonPath("$.address").value(schoolResponse.address()))
                .andExpect(jsonPath("$.phoneNumber").value(schoolResponse.phoneNumber()))
                .andExpect(jsonPath("$.email").value(schoolResponse.email()))
                .andExpect(jsonPath("$.type").value(schoolResponse.type()))
                .andExpect(jsonPath("$.establishedYear").value(schoolResponse.establishedYear()))
                .andExpect(jsonPath("$.studentCapacity").value(schoolResponse.studentCapacity()))
                .andExpect(jsonPath("$.studentCount").value(schoolResponse.studentCount()))
                .andExpect(jsonPath("$.teacherCount").value(schoolResponse.teacherCount()))
                .andExpect(jsonPath("$.staffCount").value(schoolResponse.staffCount()))
                .andExpect(jsonPath("$.classroomCount").value(schoolResponse.classroomCount()))
                .andExpect(jsonPath("$.facilities", hasItem(schoolResponse.facilities().get(0))))
                .andExpect(jsonPath("$.workTime.start").value(schoolResponse.workTime().start().toString()))
                .andExpect(jsonPath("$.workTime.end").value(schoolResponse.workTime().end().toString()))
                .andExpect(jsonPath("$.schoolHours.start").value(schoolResponse.schoolHours().start().toString()))
                .andExpect(jsonPath("$.schoolHours.end").value(schoolResponse.schoolHours().end().toString()));

        verify(schoolService).deleteSchoolById(anyLong());
    }

    @Test
    public void shouldUpdateSchoolAndReturnJsonUpdatedSchoolResponse() throws Exception {
        var updateSchoolRequestJson = objectMapper.writeValueAsString(updateSchoolRequest);

        given(schoolService.updateSchoolById(anyLong(), any(SchoolRequest.class))).willReturn(updatedSchoolResponse);

        mockMvc.perform(
                        MockMvcRequestBuilders.patch(BASE_URL + "/{school-id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateSchoolRequestJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedSchoolResponse.id()))
                .andExpect(jsonPath("$.name").value(updatedSchoolResponse.name()))
                .andExpect(jsonPath("$.region").value(updatedSchoolResponse.region()))
                .andExpect(jsonPath("$.locality").value(updatedSchoolResponse.locality()))
                .andExpect(jsonPath("$.address").value(updatedSchoolResponse.address()))
                .andExpect(jsonPath("$.phoneNumber").value(updatedSchoolResponse.phoneNumber()))
                .andExpect(jsonPath("$.email").value(updatedSchoolResponse.email()))
                .andExpect(jsonPath("$.type").value(updatedSchoolResponse.type()))
                .andExpect(jsonPath("$.establishedYear").value(updatedSchoolResponse.establishedYear()))
                .andExpect(jsonPath("$.studentCapacity").value(updatedSchoolResponse.studentCapacity()))
                .andExpect(jsonPath("$.studentCount").value(updatedSchoolResponse.studentCount()))
                .andExpect(jsonPath("$.teacherCount").value(updatedSchoolResponse.teacherCount()))
                .andExpect(jsonPath("$.staffCount").value(updatedSchoolResponse.staffCount()))
                .andExpect(jsonPath("$.classroomCount").value(updatedSchoolResponse.classroomCount()))
                .andExpect(jsonPath("$.facilities", hasItem(updatedSchoolResponse.facilities().get(0))))
                .andExpect(jsonPath("$.workTime.start").value(updatedSchoolResponse.workTime().start().toString()))
                .andExpect(jsonPath("$.workTime.end").value(updatedSchoolResponse.workTime().end().toString()))
                .andExpect(jsonPath("$.schoolHours.start").value(updatedSchoolResponse.schoolHours().start().toString()))
                .andExpect(jsonPath("$.schoolHours.end").value(updatedSchoolResponse.schoolHours().end().toString()));

        verify(schoolService).updateSchoolById(anyLong(), any(SchoolRequest.class));
    }
}
