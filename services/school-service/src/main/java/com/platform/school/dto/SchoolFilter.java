package com.platform.school.dto;


import java.util.List;

public record SchoolFilter(
        String name,
        String region,
        String locality,
        Integer studentCapacityMin,
        Integer studentCapacityMax,
        Integer studentCountMin,
        Integer studentCountMax,
        Integer teacherCountMin,
        Integer teacherCountMax,
        Integer staffCountMin,
        Integer staffCountMax,
        Integer classroomCountMin,
        Integer classroomCountMax,
        List<String> facilities,
        TimeRangeDto workTime,
        TimeRangeDto schoolHours

) {
}
