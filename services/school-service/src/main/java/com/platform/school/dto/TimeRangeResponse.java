package com.platform.school.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public record TimeRangeResponse(
        @JsonFormat(pattern = "HH:mm")
        LocalTime start,
        @JsonFormat(pattern = "HH:mm")
        LocalTime end
) {
}
