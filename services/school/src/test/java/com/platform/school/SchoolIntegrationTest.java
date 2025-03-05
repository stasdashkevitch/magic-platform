package com.platform.school;

import com.platform.school.dto.SchoolResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SchoolIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test() throws Exception {
        var response = restTemplate.getForEntity("/schools/{school-id}", SchoolResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().name()).isEqualTo("СРЕДНЯЯ ШКОЛА №3 г. Иваново");
        assertThat(response.getBody().address()).isEqualTo("ул. Советская, 26");
        assertThat(response.getBody().phoneNumber()).isEqualTo("(01652) 9 50 82");
        assertThat(response.getBody().email()).isEqualTo("sch3@ivanovo.edu.by");
        assertThat(response.getBody().type()).isEqualTo("ГУО");
        assertThat(response.getBody().establishedYear()).isEqualTo(1992);
        assertThat(response.getBody().studentCapacity()).isEqualTo(1000);
        assertThat(response.getBody().studentCount()).isEqualTo(990);
        assertThat(response.getBody().teachersCount()).isEqualTo(60);
        assertThat(response.getBody().staffCount()).isEqualTo(90);
        assertThat(response.getBody().classroomCount()).isEqualTo(60);
        assertThat(response.getBody().facilities().contains("библиотека")).isEqualTo(true);
        assertThat(response.getBody().extracurricularActivities().contains("факультатив по математике")).isEqualTo(true);
        assertThat(response.getBody().workTime().start()).isEqualTo(LocalTime.of(7, 0));
        assertThat(response.getBody().workTime().end()).isEqualTo(LocalTime.of(21, 0));
        assertThat(response.getBody().workTime().start()).isEqualTo(LocalTime.of(8, 0));
        assertThat(response.getBody().workTime().end()).isEqualTo(LocalTime.of(20, 0));
    }
}
