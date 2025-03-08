package com.platform.school.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class TimeRange {

    private LocalTime start;
    private LocalTime end;
}
