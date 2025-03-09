package com.platform.school;

import com.platform.school.dto.SchoolRequest;
import com.platform.school.dto.SchoolResponse;
import com.platform.school.dto.TimeRangeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SchoolIntegrationTest {

    private static SchoolRequest schoolRequest;

    @Autowired
    private TestRestTemplate restTemplate;

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
    }

    @Test
    public void shouldGetSchoolById() {
        var response = restTemplate.getForEntity("api/v1/schools/{school-id}", SchoolResponse.class);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().name()).isEqualTo("СРЕДНЯЯ ШКОЛА №3 г. Иваново");
        assertThat(response.getBody().address()).isEqualTo("ул. Советская, 26");
        assertThat(response.getBody().region()).isEqualTo("Брестская область");
        assertThat(response.getBody().locality()).isEqualTo("г. Иваново");
        assertThat(response.getBody().phoneNumber()).isEqualTo("(01652) 9 50 82");
        assertThat(response.getBody().email()).isEqualTo("sch3@ivanovo.edu.by");
        assertThat(response.getBody().type()).isEqualTo("ГУО");
        assertThat(response.getBody().establishedYear()).isEqualTo(1992);
        assertThat(response.getBody().studentCapacity()).isEqualTo(1000);
        assertThat(response.getBody().studentCount()).isEqualTo(990);
        assertThat(response.getBody().teachersCount()).isEqualTo(60);
        assertThat(response.getBody().staffCount()).isEqualTo(90);
        assertThat(response.getBody().classroomCount()).isEqualTo(60);
        assertThat(response.getBody().facilities().contains("Библиотека")).isEqualTo(true);
        assertThat(response.getBody().workTime().start()).isEqualTo(LocalTime.of(7, 0));
        assertThat(response.getBody().workTime().end()).isEqualTo(LocalTime.of(21, 0));
        assertThat(response.getBody().schoolHours().start()).isEqualTo(LocalTime.of(8, 0));
        assertThat(response.getBody().schoolHours().end()).isEqualTo(LocalTime.of(20, 0));
    }

    @Test
    public void shouldCreateSchool() {
        var response = restTemplate.postForEntity(
                "api/v1/schools",
                schoolRequest,
                SchoolResponse.class
        );

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().name()).isEqualTo("СРЕДНЯЯ ШКОЛА №3 г. Иваново");
        assertThat(response.getBody().address()).isEqualTo("ул. Советская, 26");
        assertThat(response.getBody().region()).isEqualTo("Брестская область");
        assertThat(response.getBody().locality()).isEqualTo("г. Иваново");
        assertThat(response.getBody().phoneNumber()).isEqualTo("(01652) 9 50 82");
        assertThat(response.getBody().email()).isEqualTo("sch3@ivanovo.edu.by");
        assertThat(response.getBody().type()).isEqualTo("ГУО");
        assertThat(response.getBody().establishedYear()).isEqualTo(1992);
        assertThat(response.getBody().studentCapacity()).isEqualTo(1000);
        assertThat(response.getBody().studentCount()).isEqualTo(990);
        assertThat(response.getBody().teachersCount()).isEqualTo(60);
        assertThat(response.getBody().staffCount()).isEqualTo(90);
        assertThat(response.getBody().classroomCount()).isEqualTo(60);
        assertThat(response.getBody().facilities().contains("Библиотека")).isEqualTo(true);
        assertThat(response.getBody().workTime().start()).isEqualTo(LocalTime.of(7, 0));
        assertThat(response.getBody().workTime().end()).isEqualTo(LocalTime.of(21, 0));
        assertThat(response.getBody().schoolHours().start()).isEqualTo(LocalTime.of(8, 0));
        assertThat(response.getBody().schoolHours().end()).isEqualTo(LocalTime.of(20, 0));
    }
}
