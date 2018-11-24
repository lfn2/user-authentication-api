package com.userauthenticationapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userauthenticationapi.exceptions.InvalidSessionException;
import com.userauthenticationapi.exceptions.InvalidUserCredentialsException;
import com.userauthenticationapi.exceptions.UserNotFoundException;
import com.userauthenticationapi.forms.LoginForm;
import com.userauthenticationapi.forms.SignUpForm;
import com.userauthenticationapi.models.PhoneNumber;
import com.userauthenticationapi.models.User;
import com.userauthenticationapi.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  private UUID id = UUID.randomUUID();
  private String name= "name";
  private String email = "email@email.com";
  private String password = "password";
  private String token = "token";
  private List<PhoneNumber> phones = Collections.singletonList(new PhoneNumber("12345", "81"));

  @Test
  public void signUp_givenInvalidPassword_returnsBadRequestStatus() throws Exception {
    password = "";
    SignUpForm signUpForm = new SignUpForm();
    signUpForm.setName(name);
    signUpForm.setEmail(email);
    signUpForm.setPassword(password);
    signUpForm.setPhones(phones);
    User user = new User();
    String requestBody = new ObjectMapper().writeValueAsString(signUpForm);

    when(userService.save(any())).thenReturn(user);
    this.mockMvc.perform(
        post("/sign_up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
    ).andExpect(status().isBadRequest());
  }

  @Test
  public void signUp_givenInvalidEmail_returnsBadRequestStatus() throws Exception {
    email = "email";
    SignUpForm signUpForm = new SignUpForm();
    signUpForm.setName(name);
    signUpForm.setEmail(email);
    signUpForm.setPassword(password);
    signUpForm.setPhones(phones);
    User user = new User();
    String requestBody = new ObjectMapper().writeValueAsString(signUpForm);

    when(userService.save(any())).thenReturn(user);
    this.mockMvc.perform(
        post("/sign_up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
    ).andExpect(status().isBadRequest());
  }

  @Test
  public void signUp_givenInvalidName_returnsBadRequestStatus() throws Exception {
    SignUpForm signUpForm = new SignUpForm();
    signUpForm.setEmail(email);
    signUpForm.setPassword(password);
    signUpForm.setPhones(phones);
    User user = new User();
    String requestBody = new ObjectMapper().writeValueAsString(signUpForm);

    when(userService.save(any())).thenReturn(user);
    this.mockMvc.perform(
        post("/sign_up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
    ).andExpect(status().isBadRequest());
  }

  @Test
  public void signUp_givenValidParameters_returnsOkStatus() throws Exception {
    SignUpForm signUpForm = new SignUpForm();
    signUpForm.setName(name);
    signUpForm.setEmail(email);
    signUpForm.setPassword(password);
    signUpForm.setPhones(phones);
    User user = new User();
    String requestBody = new ObjectMapper().writeValueAsString(signUpForm);

    when(userService.save(any())).thenReturn(user);
    this.mockMvc.perform(
        post("/sign_up")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody)
        ).andExpect(status().isCreated());
  }

  @Test
  public void login_givenInvalidCredentials_returnsUnauthorizedStatus() throws Exception {
    LoginForm loginForm = new LoginForm();
    loginForm.setEmail(email);
    loginForm.setPassword(password);
    String requestBody = new ObjectMapper().writeValueAsString(loginForm);

    when(userService.authenticate(email, password)).thenThrow(new InvalidUserCredentialsException());

    this.mockMvc.perform(
        post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
    ).andExpect(status().isUnauthorized());
  }

  @Test
  public void login_givenInvalidPassword_returnsBadRequestStatus() throws Exception {
    password = "";
    LoginForm loginForm = new LoginForm();
    loginForm.setEmail(email);
    loginForm.setPassword(password);
    String requestBody = new ObjectMapper().writeValueAsString(loginForm);
    User expectedUser = new User();

    when(userService.authenticate(email, password)).thenReturn(expectedUser);

    this.mockMvc.perform(
        post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
    ).andExpect(status().isBadRequest());
  }

  @Test
  public void login_givenInvalidEmail_returnsBadRequestStatus() throws Exception {
    email = "email";
    LoginForm loginForm = new LoginForm();
    loginForm.setEmail(email);
    loginForm.setPassword(password);
    String requestBody = new ObjectMapper().writeValueAsString(loginForm);
    User expectedUser = new User();

    when(userService.authenticate(email, password)).thenReturn(expectedUser);

    this.mockMvc.perform(
        post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
    ).andExpect(status().isBadRequest());
  }

  @Test
  public void login_givenValidCredentials_ReturnsUser() throws Exception {
    LoginForm loginForm = new LoginForm();
    loginForm.setEmail(email);
    loginForm.setPassword(password);
    String requestBody = new ObjectMapper().writeValueAsString(loginForm);
    User expectedUser = new User();

    when(userService.authenticate(email, password)).thenReturn(expectedUser);

    this.mockMvc.perform(
        post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody)
      ).andExpect(status().isOk());
  }

  @Test
  public void getUser_givenValidParameters_getsUserFromUserService() throws Exception {
    User expectedUser = new User();
    when(userService.get(id, token)).thenReturn(expectedUser);

    this.mockMvc.perform(
          get("/user/" + id.toString())
          .header("Authorization", "Bearer" + token)
        )
        .andExpect(status().isOk());
  }

  @Test
  public void getUser_withoutToken_returnsUnauthorizedStatus() throws Exception {
    this.mockMvc.perform(get("/user/" + id.toString()))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void getUser_withInvalidId_returnsUnauthorizedStatus() throws Exception {
    when(userService.get(id, token)).thenThrow(new UserNotFoundException(id));

    this.mockMvc.perform(
        get("/user/" + id.toString())
        .header("Authorization", token)
        )
        .andExpect(status().isNotFound());
  }

  @Test
  public void getUser_withExpiredToken_returnsUnauthorizedStatus() throws Exception {
    when(userService.get(id, token)).thenThrow(new InvalidSessionException());

    this.mockMvc.perform(
        get("/user/" + id.toString())
            .header("Authorization", token)
    )
        .andExpect(status().isUnauthorized());
  }
}
