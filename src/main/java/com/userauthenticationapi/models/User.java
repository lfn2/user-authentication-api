package com.userauthenticationapi.models;

import com.userauthenticationapi.forms.CreateUserForm;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class User {
  @Id
  @GeneratedValue
  private UUID id;

  private String name;

  @Column(unique = true)
  private String email;

  private String password;

  @CreationTimestamp
  private Date modified;

  @UpdateTimestamp
  private Date updated;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<PhoneNumber> phones;

  public User() { }

  public User(CreateUserForm createUserForm) {
    this.name = createUserForm.getName();
    this.email = createUserForm.getEmail();
    this.password = createUserForm.getPassword();
    this.phones = createUserForm.getPhones();
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
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

  public Date getModified() {
    return modified;
  }

  public void setModified(Date modified) {
    this.modified = modified;
  }

  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }

  public List<PhoneNumber> getPhones() {
    return phones;
  }

  public void setPhones(List<PhoneNumber> phones) {
    this.phones = phones;
  }
}
