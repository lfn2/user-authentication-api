package com.userauthenticationapi.forms;

import com.userauthenticationapi.models.PhoneNumber;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

public class SignUpForm {
  @NotBlank
  private String name;

  @Pattern(regexp = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,4}$", message = "Invalid email format")
  private String email;

  @NotBlank
  private String password;

  private List<PhoneNumber> phones;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

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

  public List<PhoneNumber> getPhones() {
    return phones;
  }

  public void setPhones(List<PhoneNumber> phones) {
    this.phones = phones;
  }
}
