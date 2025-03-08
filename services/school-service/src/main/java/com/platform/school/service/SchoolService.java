package com.platform.school.service;

import com.platform.school.dto.SchoolResponse;
import com.platform.school.exception.SchoolNotFoundException;
import com.platform.school.mapper.SchoolMapper;
import com.platform.school.repository.SchoolRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;

    public SchoolResponse getSchoolById(Long id) {
        log.info("Fetching school by ID: {}", id);

        var school = schoolRepository.findById(id).orElse(null);
        if (school == null) {
            log.error("School not found. ID: {}", id);
            throw new SchoolNotFoundException("School not found with id: " + id);
        }

        var schoolResponse = schoolMapper.schoolToSchoolResponse(school);

        log.info("Successfully fetched school. ID: {}", id);
        return schoolResponse;
    }
}
