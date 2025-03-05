package com.platform.school.controller;

import com.platform.school.dto.SchoolResponse;
import com.platform.school.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schools")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping("/{school-id}")
    public ResponseEntity<SchoolResponse> getSchoolById(
            @PathVariable("school-id") Long id
    ) {
        return ResponseEntity.ok(schoolService.getSchool(id));
    }
}
