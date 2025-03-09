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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SchoolController.class)
public class SchoolControllerTest {

    private final static String BASE_URL = "/api/v1/schools";

    private static SchoolResponse schoolResponse;
    private static SchoolRequest schoolRequest;

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
    }

    @Test
    public void shouldReturnJsonSchoolResponseById() throws Exception {

        given(schoolService.getSchoolById(anyLong())).willReturn(schoolResponse);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL + "/{school-id}", 1)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("СРЕДНЯЯ ШКОЛА №3 г. Иваново"))
                .andExpect(jsonPath("$.region").value("Брестская область"))
                .andExpect(jsonPath("$.locality").value("г. Иваново"))
                .andExpect(jsonPath("$.address").value("ул. Советская, 26"))
                .andExpect(jsonPath("$.phoneNumber").value("(01652) 9 50 82"))
                .andExpect(jsonPath("$.email").value("sch3@ivanovo.edu.by"))
                .andExpect(jsonPath("$.phoneNumber").value("(01652) 9 50 82"))
                .andExpect(jsonPath("$.email").value("sch3@ivanovo.edu.by"))
                .andExpect(jsonPath("$.type").value("ГУО"))
                .andExpect(jsonPath("$.establishedYear").value(1992))
                .andExpect(jsonPath("$.studentCapacity").value(1000))
                .andExpect(jsonPath("$.studentCount").value(9090))
                .andExpect(jsonPath("$.teachersCount").value(60))
                .andExpect(jsonPath("$.staffCount").value(90))
                .andExpect(jsonPath("$.classroomCount").value(60))
                .andExpect(jsonPath("$.facilities", hasItem("Библиотека")))
                .andExpect(jsonPath("$.workTime.start").value("07:00"))
                .andExpect(jsonPath("$.workTime.end").value("21:00"))
                .andExpect(jsonPath("$.schoolHours.start").value("08:00"))
                .andExpect(jsonPath("$.schoolHours.end").value("20:00"));
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
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("СРЕДНЯЯ ШКОЛА №3 г. Иваново"))
                .andExpect(jsonPath("$.region").value("Брестская область"))
                .andExpect(jsonPath("$.locality").value("г. Иваново"))
                .andExpect(jsonPath("$.address").value("ул. Советская, 26"))
                .andExpect(jsonPath("$.phoneNumber").value("(01652) 9 50 82"))
                .andExpect(jsonPath("$.email").value("sch3@ivanovo.edu.by"))
                .andExpect(jsonPath("$.phoneNumber").value("(01652) 9 50 82"))
                .andExpect(jsonPath("$.email").value("sch3@ivanovo.edu.by"))
                .andExpect(jsonPath("$.type").value("ГУО"))
                .andExpect(jsonPath("$.establishedYear").value(1992))
                .andExpect(jsonPath("$.studentCapacity").value(1000))
                .andExpect(jsonPath("$.studentCount").value(9090))
                .andExpect(jsonPath("$.teachersCount").value(60))
                .andExpect(jsonPath("$.staffCount").value(90))
                .andExpect(jsonPath("$.classroomCount").value(60))
                .andExpect(jsonPath("$.facilities", hasItem("Библиотека")))
                .andExpect(jsonPath("$.workTime.start").value("07:00"))
                .andExpect(jsonPath("$.workTime.end").value("21:00"))
                .andExpect(jsonPath("$.schoolHours.start").value("08:00"))
                .andExpect(jsonPath("$.schoolHours.end").value("20:00"));
    }

    @Test
    public void shouldDeleteSchoolByIdAndReturnJsonSchoolResponse() throws Exception {
        given(schoolService.deleteSchoolById(anyLong())).willReturn(schoolResponse);
        mockMvc.perform(
                MockMvcRequestBuilders.delete(BASE_URL + "/{school-id}", 1)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("СРЕДНЯЯ ШКОЛА №3 г. Иваново"))
                .andExpect(jsonPath("$.region").value("Брестская область"))
                .andExpect(jsonPath("$.locality").value("г. Иваново"))
                .andExpect(jsonPath("$.address").value("ул. Советская, 26"))
                .andExpect(jsonPath("$.phoneNumber").value("(01652) 9 50 82"))
                .andExpect(jsonPath("$.email").value("sch3@ivanovo.edu.by"))
                .andExpect(jsonPath("$.phoneNumber").value("(01652) 9 50 82"))
                .andExpect(jsonPath("$.email").value("sch3@ivanovo.edu.by"))
                .andExpect(jsonPath("$.type").value("ГУО"))
                .andExpect(jsonPath("$.establishedYear").value(1992))
                .andExpect(jsonPath("$.studentCapacity").value(1000))
                .andExpect(jsonPath("$.studentCount").value(9090))
                .andExpect(jsonPath("$.teachersCount").value(60))
                .andExpect(jsonPath("$.staffCount").value(90))
                .andExpect(jsonPath("$.classroomCount").value(60))
                .andExpect(jsonPath("$.facilities", hasItem("Библиотека")))
                .andExpect(jsonPath("$.workTime.start").value("07:00"))
                .andExpect(jsonPath("$.workTime.end").value("21:00"))
                .andExpect(jsonPath("$.schoolHours.start").value("08:00"))
                .andExpect(jsonPath("$.schoolHours.end").value("20:00"));
    }
}
