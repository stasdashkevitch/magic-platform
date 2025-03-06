package com.platform.school.dto;

import java.util.List;

public record SchoolResponse(
        Long id,
        String name,
        LocationResponse location,
        String address,
        String phoneNumber,
        String email,
        String type,
        Integer establishedYear,
        Integer studentCapacity,
        Integer studentCount,
        Integer teachersCount,
        Integer staffCount,
        Integer classroomCount,
        List<String> facilities,
        List<String> extracurricularActivities,
        TimeRangeDto workTime,
        TimeRangeDto schoolHours
) {
}
