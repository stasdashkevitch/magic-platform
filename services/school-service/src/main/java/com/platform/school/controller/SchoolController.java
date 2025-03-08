package com.platform.school.controller;

import com.platform.school.dto.SchoolResponse;
import com.platform.school.service.SchoolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/schools")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping("/{school-id}")
    public ResponseEntity<SchoolResponse> getSchoolById(
            @PathVariable("school-id") Long id
    ) {
        log.info("Get school by id: {}", id);
        return ResponseEntity.ok(schoolService.getSchoolById(id));
    }
}
