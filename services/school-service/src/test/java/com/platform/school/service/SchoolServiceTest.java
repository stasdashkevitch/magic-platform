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
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SchoolServiceTest {

    private static School school;
    private static SchoolResponse schoolResponse;
    private static SchoolRequest schoolRequest;

    @Mock
    private SchoolRepository schoolRepository;
    @Mock
    private SchoolMapper schoolMapper;
    @InjectMocks
    private SchoolService schoolService;

    @BeforeEach
    public void setUp() {
        school = School.builder()
                .id(1L)
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
                .teachersCount(60)
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
    }

    @Test
    public void shouldReturnSchoolResponseById() {
        given(schoolRepository.findById(1L)).willReturn(Optional.of(school));
        given(schoolMapper.schoolToSchoolResponse(school)).willReturn(
            schoolResponse
        );

        var resultSchoolResponse = schoolService.getSchoolById(1L);

        assertThat(schoolResponse).isNotNull();
        assertThat(resultSchoolResponse).isEqualTo(schoolResponse);
    }

    @Test
    public void shouldCreateSchoolAndReturnSchoolResponse() {
        given(schoolMapper.schoolRequestToSchool(schoolRequest)).willReturn(school);
        given(schoolRepository.save(school)).willReturn(school);
        given(schoolMapper.schoolToSchoolResponse(school)).willReturn(schoolResponse);

        var resultSchoolResponse = schoolService.createSchool(schoolRequest);

        assertThat(resultSchoolResponse).isNotNull();
        assertThat(resultSchoolResponse).isEqualTo(schoolResponse);
    }
}