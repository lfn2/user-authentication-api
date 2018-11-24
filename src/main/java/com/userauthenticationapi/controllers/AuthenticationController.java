package com.userauthenticationapi.controllers;

import com.userauthenticationapi.forms.CreateUserForm;
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

  @GetMapping("/user/{id}")
  public User findUser(@PathVariable UUID id) {
    User user = userService.findByUserId(id);
    return user;
  }

  @PostMapping("/sign_up")
  public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserForm createUserForm) {
    User user = userService.save(new User(createUserForm));

    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }
}
