package com.userauthenticationapi.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ApiResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler
  public final ResponseEntity<Object> handleInternalServerError(Exception e, WebRequest webRequest) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());

    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler
  public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e, WebRequest webRequest) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());

    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public final ResponseEntity<Object> handleUserEmailAlreadyRegistered(
      UserEmailAlreadyRegisteredException e, WebRequest webRequest) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request
  ) {
    ExceptionResponse exceptionResponse = new ExceptionResponse("Validation failed", e.getMessage());

    return new ResponseEntity<>(exceptionResponse, status);
  }
}
