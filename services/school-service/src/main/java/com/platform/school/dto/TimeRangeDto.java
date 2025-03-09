package com.platform.school.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record TimeRangeDto(
        @JsonFormat(pattern = "HH:mm")
        @NotNull(message = "Start time is required")
        LocalTime start,
        @JsonFormat(pattern = "HH:mm")
        @NotNull(message = "End time is required")
        LocalTime end
) {
}
