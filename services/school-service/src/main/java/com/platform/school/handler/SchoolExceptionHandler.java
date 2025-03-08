package com.platform.school.handler;

import com.platform.school.dto.SchoolErrorResponse;
import com.platform.school.exception.SchoolNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SchoolExceptionHandler {

    @ExceptionHandler(SchoolNotFoundException.class)
    public ResponseEntity<SchoolErrorResponse> handleSchoolNotFoundException(SchoolNotFoundException ex) {
        return new ResponseEntity<>(
                    new SchoolErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
                HttpStatus.NOT_FOUND);
    }
}
