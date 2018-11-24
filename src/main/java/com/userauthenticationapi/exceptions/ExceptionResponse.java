package com.userauthenticationapi.exceptions;

public class ExceptionResponse {
  private String message;
  private String details;

  public ExceptionResponse(String message) {
    this.message = message;
  }

  public ExceptionResponse(String message, String details) {
    this(message);
    this.details = details;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }
}
