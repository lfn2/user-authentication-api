package com.userauthenticationapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class PhoneNumber {
  @Id
  @GeneratedValue
  @JsonIgnore
  private Long id;

  private String number;

  private String ddd;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  @JsonIgnore
  private User user;

  public PhoneNumber() {}

  public PhoneNumber(String number, String ddd) {
    this.number = number;
    this.ddd = ddd;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getDdd() {
    return ddd;
  }

  public void setDdd(String ddd) {
    this.ddd = ddd;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
