package com.userauthenticationapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.userauthenticationapi.forms.CreateUserForm;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(indexes = {@Index(name="email_index", columnList = "email",unique = true)})
public class User {
  @Id
  @GeneratedValue
  @JsonIgnore
  private Long id;

  private UUID userId;

  private String name;

  private String email;

  private String password;

  @CreationTimestamp
  private Date created;

  @UpdateTimestamp
  private Date modified;

  @CreationTimestamp
  private Date lastLogin;

  @Transient
  private String token;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<PhoneNumber> phones;

  public User() { }

  public User(CreateUserForm createUserForm) {
    this.name = createUserForm.getName();
    this.email = createUserForm.getEmail();
    this.password = createUserForm.getPassword();
    this.phones = createUserForm.getPhones();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getModified() {
    return modified;
  }

  public void setModified(Date modified) {
    this.modified = modified;
  }

  public List<PhoneNumber> getPhones() {
    return phones;
  }

  public void setPhones(List<PhoneNumber> phones) {
    this.phones = phones;
  }

  public Date getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(Date lastLogin) {
    this.lastLogin = lastLogin;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }
}
