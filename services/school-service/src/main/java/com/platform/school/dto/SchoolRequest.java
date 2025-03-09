package com.platform.school.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record SchoolRequest(
        @NotNull(message = "Name is required")
        @NotBlank(message = "Name  not be empty")
        String name,
        @NotNull(message = "Region is required")
        @NotBlank(message = "Region cannot not be empty")
        String region,
        @NotNull(message = "Locality is required")
        @NotBlank(message = "Locality cannot not be empty")
        String locality,
        @NotNull(message = "Address is required")
        @NotBlank(message = "Address must not be empty")
        String address,
        @NotNull(message = "Phone number is required")
        @NotBlank(message = "Phone number cannot be blank")
        @Pattern(regexp = "^\\(?\\d{2,5}\\)?[- ]?\\d{1,4}[- ]?\\d{1,4}[- ]?\\d{1,4}$", message = "Phone number must be valid")
        String phoneNumber,
        @NotNull(message = "Email is required")
        @NotBlank(message = "Email cannot not be empty")
        @Email
        String email,
        @NotNull(message = "Type is required")
        @NotBlank(message = "Type cannot not be empty")
        String type,
        @Positive(message = "Established year cannot be negative")
        Integer establishedYear,
        @Positive(message = "Students capacity cannot be negative")
        Integer studentCapacity,
        @Positive(message = "Students count cannot be negative")
        Integer studentCount,
        @Positive(message = "Teachers count cannot be negative")
        Integer teacherCount,
        @Positive(message = "Staff count cannot be negative")
        Integer staffCount,
        @Positive(message = "Classrooms count cannot be negative")
        Integer classroomCount,
        List<String> facilities,
        @Valid
        TimeRangeDto workTime,
        @Valid
        TimeRangeDto schoolHours
) {
}
