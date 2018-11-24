package com.userauthenticationapi.controllers;

import com.userauthenticationapi.exceptions.UnauthorizedException;
import com.userauthenticationapi.forms.SignUpForm;
import com.userauthenticationapi.forms.LoginForm;
import com.userauthenticationapi.models.User;
import com.userauthenticationapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class AuthenticationController {
  @Autowired
  private UserService userService;

  @GetMapping("/")
  public String index() {
    return "User Authentication Api!";
  }

  @PostMapping("/sign_up")
  public ResponseEntity<User> signUp(@Valid @RequestBody SignUpForm signUpForm) {
    User user = userService.save(new User(signUpForm));

    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<User> login(@Valid @RequestBody LoginForm loginForm) {
    User user = userService.authenticate(loginForm.getEmail(), loginForm.getPassword());

    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @GetMapping("/user/{id}")
  public User getUser(@PathVariable UUID id, @RequestHeader(value = "Authorization", required = false) String token) {
    if (token == null || token.isEmpty()) {
      throw new UnauthorizedException();
    } else if (token.startsWith("Bearer")) {
      token = token.replace("Bearer ", "");
    }

    return userService.get(id, token);
  }
}
