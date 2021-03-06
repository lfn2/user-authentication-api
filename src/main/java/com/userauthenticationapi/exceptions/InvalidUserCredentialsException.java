package com.userauthenticationapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidUserCredentialsException extends UnauthorizedException {
  public InvalidUserCredentialsException() {
    super("Invalid email or password");
  }
}
