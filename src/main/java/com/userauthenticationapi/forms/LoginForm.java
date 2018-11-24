package com.userauthenticationapi.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class LoginForm {
  @Pattern(regexp = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,4}$", message = "Invalid email format")
  private String email;

  @NotBlank
  private String password;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
