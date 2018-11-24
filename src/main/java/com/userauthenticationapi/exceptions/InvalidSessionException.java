package com.userauthenticationapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidSessionException extends UnauthorizedException {
  public InvalidSessionException() {
    super("Invalid session");
  }
}

