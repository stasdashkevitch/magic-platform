package com.platform.school.dto;

import java.util.List;

public record SchoolResponse(
        Long id,
        String name,
        String region,
        String locality,
        String address,
        String phoneNumber,
        String email,
        String type,
        Integer establishedYear,
        Integer studentCapacity,
        Integer studentCount,
        Integer teacherCount,
        Integer staffCount,
        Integer classroomCount,
        List<String> facilities,
        TimeRangeDto workTime,
        TimeRangeDto schoolHours
) {
}
