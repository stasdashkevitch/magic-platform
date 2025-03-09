package com.platform.school.handler;

import com.platform.school.controller.SchoolController;
import com.platform.school.exception.SchoolNotFoundException;
import com.platform.school.service.SchoolService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SchoolController.class)
public class SchoolExceptionHandlerTest {

    private final static Long SCHOOL_ID = 1L;

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    SchoolService schoolService;

    @Test
    public void shouldThrowNotFoundSchoolById() throws Exception {
        given(schoolService.getSchoolById(anyLong())).willThrow(new SchoolNotFoundException("School not found. ID: " + SCHOOL_ID));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/schools/{school-id}", SCHOOL_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("School not found. ID: " + SCHOOL_ID));
    }
}
