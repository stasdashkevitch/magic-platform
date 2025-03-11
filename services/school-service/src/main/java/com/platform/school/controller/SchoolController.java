package com.platform.school.controller;

import com.platform.school.dto.*;
import com.platform.school.service.SchoolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<PageResponse<SchoolResponse>> getAllSchools(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String locality,
            @RequestParam(required = false) Integer studentCapacityMin,
            @RequestParam(required = false) Integer studentCapacityMax,
            @RequestParam(required = false) Integer studentCountMin,
            @RequestParam(required = false) Integer studentCountMax,
            @RequestParam(required = false) Integer teacherCountMin,
            @RequestParam(required = false) Integer teacherCountMax,
            @RequestParam(required = false) Integer staffCountMin,
            @RequestParam(required = false) Integer staffCountMax,
            @RequestParam(required = false) Integer classroomCountMin,
            @RequestParam(required = false) Integer classroomCountMax,
            @RequestParam(required = false) List<String> facilities,
            @RequestParam(required = false) LocalTime workTimeStart,
            @RequestParam(required = false) LocalTime workTimeEnd,
            @RequestParam(required = false) LocalTime schoolHoursStart,
            @RequestParam(required = false) LocalTime schoolHoursEnd,
            Pageable pageable
    ) {
        SchoolFilter schoolFilter = new SchoolFilter(
                name,
                region,
                locality,
                studentCapacityMin,
                studentCapacityMax,
                studentCountMin,
                studentCountMax,
                teacherCountMin,
                teacherCountMax,
                staffCountMin,
                staffCountMax,
                classroomCountMin,
                classroomCountMax,
                facilities,
                new TimeRangeDto(workTimeStart, workTimeEnd),
                new TimeRangeDto(schoolHoursStart, schoolHoursEnd)
        );

        return ResponseEntity.ok(schoolService.findAll(schoolFilter, pageable));
    }
}
