package com.platform.school.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SchoolNotFoundException extends RuntimeException {

    private final String message;
}
