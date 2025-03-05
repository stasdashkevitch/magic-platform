package com.platform.school.dto;

import java.time.LocalTime;

public record TimeRangeDto(
        LocalTime start,
        LocalTime end
) {
}
