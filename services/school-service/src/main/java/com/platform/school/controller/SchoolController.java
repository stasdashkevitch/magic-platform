package com.platform.school.controller;

import com.platform.school.dto.SchoolRequest;
import com.platform.school.dto.SchoolResponse;
import com.platform.school.service.SchoolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<SchoolResponse> createSchool(
            @Valid @RequestBody SchoolRequest schoolRequest
    ) {
        log.info("Create school: {}", schoolRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(schoolService.createSchool(schoolRequest));
    }

    @DeleteMapping("/{school-id}")
    public ResponseEntity<SchoolResponse> deleteSchoolById(
            @PathVariable("school-id") Long id
    ) {
        log.info("Delete school by id: {}", id);
        return ResponseEntity.ok(schoolService.deleteSchoolById(id));
    }

    @PatchMapping("/{school-id}")
    public ResponseEntity<SchoolResponse> updateSchoolById(
            @PathVariable("school-id") Long id,
            @RequestBody SchoolRequest schoolRequest
    ) {
        log.info("Update(patch) school by id: {}", id);
        return ResponseEntity.ok(schoolService.updateSchoolById(id, schoolRequest));
    }
}
