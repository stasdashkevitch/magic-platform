package com.platform.school;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.school.dto.SchoolRequest;
import com.platform.school.dto.SchoolResponse;
import com.platform.school.dto.TimeRangeDto;
import com.platform.school.entity.School;
import com.platform.school.entity.SchoolLocation;
import com.platform.school.entity.TimeRange;
import com.platform.school.repository.SchoolRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class SchoolIntegrationTest {

    private static SchoolRequest schoolRequest;
    private static SchoolRequest updateSchoolRequest;
    private static School school;
    private static School secondSchool;
    private static School thirdSchool;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
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

       school = School.builder()
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

        secondSchool = School.builder()
                .name("СРЕДНЯЯ ШКОЛА №12 г. Брест")
                .location(
                        SchoolLocation.builder()
                                .id(1L)
                                .region("Брестская область")
                                .locality("г. Брест")
                                .build()
                )
                .address("ул. Советская, 26")
                .phoneNumber("(01652) 9 51 81")
                .email("sch12@ivanovo.edu.by")
                .type("ГУО")
                .establishedYear(1900)
                .studentCount(2000)
                .studentCount(1900)
                .teacherCount(120)
                .staffCount(160)
                .classroomCount(100)
                .facilities(List.of("Библиотека", "Столовая"))
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

        thirdSchool = School.builder()
                .name("СРЕДНЯЯ ШКОЛА №1 г. Минск")
                .location(
                        SchoolLocation.builder()
                                .id(1L)
                                .region("Минская область")
                                .locality("г. Минск")
                                .build()
                )
                .address("ул. Советская, 26")
                .phoneNumber("(01652) 9 51 81")
                .email("sch1@ivanovo.edu.by")
                .type("ГУО")
                .establishedYear(1890)
                .studentCount(3000)
                .studentCount(2500)
                .teacherCount(120)
                .staffCount(190)
                .classroomCount(160)
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
    }

    @Test
    public void shouldReturnSchoolById() {
        var savedSchool = schoolRepository.save(school);

        var response = restTemplate.getForEntity("api/v1/schools/" + savedSchool.getId(), SchoolResponse.class);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().id()).isEqualTo(savedSchool.getId());
        assertThat(response.getBody().name()).isEqualTo(school.getName());
        assertThat(response.getBody().region()).isEqualTo(school.getLocation().getRegion());
        assertThat(response.getBody().locality()).isEqualTo(school.getLocation().getLocality());
        assertThat(response.getBody().address()).isEqualTo(school.getAddress());
        assertThat(response.getBody().phoneNumber()).isEqualTo(school.getPhoneNumber());
        assertThat(response.getBody().email()).isEqualTo(school.getEmail());
        assertThat(response.getBody().type()).isEqualTo(school.getType());
        assertThat(response.getBody().establishedYear()).isEqualTo(school.getEstablishedYear());
        assertThat(response.getBody().studentCapacity()).isEqualTo(school.getStudentCapacity());
        assertThat(response.getBody().studentCount()).isEqualTo(school.getStudentCount());
        assertThat(response.getBody().teacherCount()).isEqualTo(school.getTeacherCount());
        assertThat(response.getBody().staffCount()).isEqualTo(school.getStaffCount());
        assertThat(response.getBody().classroomCount()).isEqualTo(school.getClassroomCount());
        assertThat(response.getBody().facilities().contains(school.getFacilities().get(0))).isEqualTo(true);
        assertThat(response.getBody().workTime().start()).isEqualTo(school.getWorkTime().getStart());
        assertThat(response.getBody().workTime().end()).isEqualTo(school.getWorkTime().getEnd());
        assertThat(response.getBody().schoolHours().start()).isEqualTo(school.getSchoolHours().getStart());
        assertThat(response.getBody().schoolHours().end()).isEqualTo(school.getSchoolHours().getEnd());
    }

    @Test
    public void shouldCreateSchoolAndReturnNewSchool() {
        var response = restTemplate.postForEntity(
                "api/v1/schools",
                schoolRequest,
                SchoolResponse.class
        );

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().id()).isPositive();
        assertThat(response.getBody().name()).isEqualTo(schoolRequest.name());
        assertThat(response.getBody().address()).isEqualTo(schoolRequest.address());
        assertThat(response.getBody().region()).isEqualTo(schoolRequest.region());
        assertThat(response.getBody().locality()).isEqualTo(schoolRequest.locality());
        assertThat(response.getBody().phoneNumber()).isEqualTo(schoolRequest.phoneNumber());
        assertThat(response.getBody().email()).isEqualTo(schoolRequest.email());
        assertThat(response.getBody().type()).isEqualTo(schoolRequest.type());
        assertThat(response.getBody().establishedYear()).isEqualTo(schoolRequest.establishedYear());
        assertThat(response.getBody().studentCapacity()).isEqualTo(schoolRequest.studentCapacity());
        assertThat(response.getBody().studentCount()).isEqualTo(schoolRequest.studentCount());
        assertThat(response.getBody().teacherCount()).isEqualTo(schoolRequest.teacherCount());
        assertThat(response.getBody().staffCount()).isEqualTo(schoolRequest.staffCount());
        assertThat(response.getBody().classroomCount()).isEqualTo(schoolRequest.classroomCount());
        assertThat(response.getBody().facilities().contains(schoolRequest.facilities().get(0))).isEqualTo(true);
        assertThat(response.getBody().workTime().start()).isEqualTo(schoolRequest.workTime().start());
        assertThat(response.getBody().workTime().end()).isEqualTo(schoolRequest.workTime().end());
        assertThat(response.getBody().schoolHours().start()).isEqualTo(schoolRequest.schoolHours().start());
        assertThat(response.getBody().schoolHours().end()).isEqualTo(schoolRequest.schoolHours().end());
    }

    @Test
    public void shouldDeleteSchoolByIdAndReturnDeletedSchool() {
        var savedSchool = schoolRepository.save(school);

        var response = restTemplate.exchange(
                "api/v1/schools/" + savedSchool.getId(),
                HttpMethod.DELETE,
                null,
                SchoolResponse.class
        );

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().id()).isEqualTo(savedSchool.getId());
        assertThat(response.getBody().name()).isEqualTo(school.getName());
        assertThat(response.getBody().region()).isEqualTo(school.getLocation().getRegion());
        assertThat(response.getBody().locality()).isEqualTo(school.getLocation().getLocality());
        assertThat(response.getBody().address()).isEqualTo(school.getAddress());
        assertThat(response.getBody().phoneNumber()).isEqualTo(school.getPhoneNumber());
        assertThat(response.getBody().email()).isEqualTo(school.getEmail());
        assertThat(response.getBody().type()).isEqualTo(school.getType());
        assertThat(response.getBody().establishedYear()).isEqualTo(school.getEstablishedYear());
        assertThat(response.getBody().studentCapacity()).isEqualTo(school.getStudentCapacity());
        assertThat(response.getBody().studentCount()).isEqualTo(school.getStudentCount());
        assertThat(response.getBody().teacherCount()).isEqualTo(school.getTeacherCount());
        assertThat(response.getBody().staffCount()).isEqualTo(school.getStaffCount());
        assertThat(response.getBody().classroomCount()).isEqualTo(school.getClassroomCount());
        assertThat(response.getBody().facilities().contains(school.getFacilities().get(0))).isEqualTo(true);
        assertThat(response.getBody().workTime().start()).isEqualTo(school.getWorkTime().getStart());
        assertThat(response.getBody().workTime().end()).isEqualTo(school.getWorkTime().getEnd());
        assertThat(response.getBody().schoolHours().start()).isEqualTo(school.getSchoolHours().getStart());
        assertThat(response.getBody().schoolHours().end()).isEqualTo(school.getSchoolHours().getEnd());
    }

    @Test
    public void shouldUpdateSchoolAndReturnUpdatedSchoolResponse() throws JsonProcessingException {
        var savedSchool = schoolRepository.save(school);
        var patchRequestJson = objectMapper.writeValueAsString(updateSchoolRequest);
        var requestEntity = new HttpEntity<>(patchRequestJson);

        ResponseEntity<SchoolResponse> response = restTemplate.exchange(
                "/api/v1/schools/" + savedSchool.getId(),
                HttpMethod.PATCH,
                requestEntity,
                SchoolResponse.class
        );

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().id()).isEqualTo(savedSchool.getId());
        // updated value
        assertThat(response.getBody().phoneNumber()).isEqualTo(updateSchoolRequest.phoneNumber());
        // old values
        assertThat(response.getBody().name()).isEqualTo(school.getName());
        assertThat(response.getBody().region()).isEqualTo(school.getLocation().getRegion());
        assertThat(response.getBody().locality()).isEqualTo(school.getLocation().getLocality());
        assertThat(response.getBody().address()).isEqualTo(school.getAddress());
        assertThat(response.getBody().email()).isEqualTo(school.getEmail());
        assertThat(response.getBody().type()).isEqualTo(school.getType());
        assertThat(response.getBody().establishedYear()).isEqualTo(school.getEstablishedYear());
        assertThat(response.getBody().studentCapacity()).isEqualTo(school.getStudentCapacity());
        assertThat(response.getBody().studentCount()).isEqualTo(school.getStudentCount());
        assertThat(response.getBody().teacherCount()).isEqualTo(school.getTeacherCount());
        assertThat(response.getBody().staffCount()).isEqualTo(school.getStaffCount());
        assertThat(response.getBody().classroomCount()).isEqualTo(school.getClassroomCount());
        assertThat(response.getBody().facilities().contains(school.getFacilities().get(0))).isEqualTo(true);
        assertThat(response.getBody().workTime().start()).isEqualTo(school.getWorkTime().getStart());
        assertThat(response.getBody().workTime().end()).isEqualTo(school.getWorkTime().getEnd());
        assertThat(response.getBody().schoolHours().start()).isEqualTo(school.getSchoolHours().getStart());
        assertThat(response.getBody().schoolHours().end()).isEqualTo(school.getSchoolHours().getEnd());
    }

    @Test
    public void shouldReturnPagedAndFilteredResult() {
        schoolRepository.save(school);
        schoolRepository.save(secondSchool);
        schoolRepository.save(thirdSchool);

    }
}
