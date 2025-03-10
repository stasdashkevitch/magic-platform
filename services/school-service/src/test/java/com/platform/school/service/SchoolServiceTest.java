package com.platform.school.service;

import com.platform.school.dto.SchoolRequest;
import com.platform.school.dto.SchoolResponse;
import com.platform.school.dto.TimeRangeDto;
import com.platform.school.entity.School;
import com.platform.school.entity.SchoolLocation;
import com.platform.school.entity.TimeRange;
import com.platform.school.mapper.SchoolMapper;
import com.platform.school.repository.SchoolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SchoolServiceTest {

    private static final Long SCHOOL_ID = 1L;

    private static School school;
    private static SchoolResponse schoolResponse;
    private static SchoolRequest schoolRequest;
    private static SchoolRequest updateSchoolRequest;
    private static SchoolResponse updatedSchoolResponse;

    @Mock
    private SchoolRepository schoolRepository;
    @Mock
    private SchoolMapper schoolMapper;
    @InjectMocks
    private SchoolService schoolService;

    @BeforeEach
    public void setUp() {
        school = School.builder()
                .id(SCHOOL_ID)
                .name("СРЕДНЯЯ ШКОЛА №3 г. Иваново")
                .location(
                        SchoolLocation.builder()
                                .id(1L)
                                .region("Брестская область")
                                .locality("г. Иваново")
                                .build()
                )
                .address("ул. Советская, 26")
                .phoneNumber("(01652) 9 50 82")
                .email("sch3@ivanovo.edu.by")
                .type("ГУО")
                .establishedYear(1992)
                .studentCount(1000)
                .studentCount(9090)
                .teacherCount(60)
                .staffCount(90)
                .classroomCount(60)
                .facilities(List.of("Библиотека"))
                .workTime(
                        TimeRange.builder()
                                .start(LocalTime.of(7, 0))
                                .end(LocalTime.of(21, 0))
                                .build()
                )
                .schoolHours(
                        TimeRange.builder()
                                .start(LocalTime.of(8, 0))
                                .end(LocalTime.of(20, 0))
                                .build()
                )
                .build();

        schoolResponse = new SchoolResponse(
                SCHOOL_ID,
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
                        LocalTime.of(2, 0)
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
    public void shouldReturnSchoolResponseById() {
        given(schoolRepository.findById(anyLong())).willReturn(Optional.of(school));
        given(schoolMapper.schoolToSchoolResponse(any(School.class))).willReturn(schoolResponse);

        var resultSchoolResponse = schoolService.getSchoolById(anyLong());

        assertThat(schoolResponse).isNotNull();
        assertThat(resultSchoolResponse).isEqualTo(schoolResponse);

        verify(schoolRepository).findById(anyLong());
        verify(schoolMapper).schoolToSchoolResponse(any(School.class));
    }

    @Test
    public void shouldCreateSchoolAndReturnSchoolResponse() {
        given(schoolMapper.schoolRequestToSchool(any(SchoolRequest.class))).willReturn(school);
        given(schoolRepository.save(any(School.class))).willReturn(school);
        given(schoolMapper.schoolToSchoolResponse(any(School.class))).willReturn(schoolResponse);

        var resultSchoolResponse = schoolService.createSchool(schoolRequest);

        assertThat(resultSchoolResponse).isNotNull();
        assertThat(resultSchoolResponse).isEqualTo(schoolResponse);

        verify(schoolMapper).schoolRequestToSchool(any(SchoolRequest.class));
        verify(schoolRepository).save(any(School.class));
        verify(schoolMapper).schoolToSchoolResponse(any(School.class));
    }

    @Test
    public void shouldDeleteSchoolByIdAndReturnSchoolResponse() {
        given(schoolRepository.findById(anyLong())).willReturn(Optional.of(school));
        doNothing().when(schoolRepository).deleteById(anyLong());
        given(schoolMapper.schoolToSchoolResponse(any(School.class))).willReturn(schoolResponse);

        var resultSchoolResponse = schoolService.deleteSchoolById(SCHOOL_ID);

        assertThat(resultSchoolResponse).isNotNull();
        assertThat(resultSchoolResponse).isEqualTo(schoolResponse);

        verify(schoolRepository).findById(anyLong());
        verify(schoolRepository).deleteById(anyLong());
        verify(schoolMapper).schoolToSchoolResponse(any(School.class));
    }

    @Test
    public void shouldUpdateSchoolByIdAndReturnUpdatedResponse() {
        given(schoolRepository.findById(anyLong())).willReturn(Optional.of(school));
        given(schoolRepository.save(any(School.class))).willReturn(school);
    given(schoolMapper.schoolToSchoolResponse(any(School.class))).willReturn(updatedSchoolResponse);

        var resultSchoolResponse = schoolService.updateSchoolById(SCHOOL_ID, updateSchoolRequest);

        assertThat(resultSchoolResponse).isNotNull();
        assertThat(resultSchoolResponse).isEqualTo(updatedSchoolResponse);

        verify(schoolRepository).findById(anyLong());
        verify(schoolRepository).save(any(School.class));
        verify(schoolMapper).schoolToSchoolResponse(any(School.class));
    }
}