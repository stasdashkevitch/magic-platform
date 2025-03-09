package com.platform.school.dto;

import java.util.List;

public record SchoolErrorResponse(
  int status,
  String message,
  List<String> errors
) {

    public SchoolErrorResponse(int status, String message) {
        this(status, message, null);
    }
}
