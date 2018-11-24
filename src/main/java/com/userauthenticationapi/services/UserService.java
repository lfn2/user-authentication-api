package com.userauthenticationapi.services;

import com.userauthenticationapi.exceptions.UserEmailAlreadyRegisteredException;
import com.userauthenticationapi.exceptions.UserNotFoundException;
import com.userauthenticationapi.forms.CreateUserForm;
import com.userauthenticationapi.models.User;
import com.userauthenticationapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public User findUser(UUID id) {
    Optional<User> user = userRepository.findById(id);

    if (!user.isPresent()) {
      throw new UserNotFoundException(id);
    }

    return user.get();
  }

  public User createUser(CreateUserForm createUserForm) {
    User user = new User(createUserForm);

    if (isEmailRegistered(user)) {
      throw new UserEmailAlreadyRegisteredException();
    }

    return userRepository.save(user);
  }

  private boolean isEmailRegistered(User user) {
    ExampleMatcher emailMatcher = ExampleMatcher.matching()
        .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.ignoreCase());
    Example<User> example = Example.of(user, emailMatcher);

    return userRepository.exists(example);
  }
}
