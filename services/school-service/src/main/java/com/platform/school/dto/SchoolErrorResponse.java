package com.platform.school.dto;

public record SchoolErrorResponse(
  int status,
  String message
) {

}
