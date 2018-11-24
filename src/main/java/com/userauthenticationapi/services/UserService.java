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

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class UserService {
  private static final long USER_TOKEN_EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(30);

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TokenService tokenService;

  public UserService() {}

  public User findByUserId(UUID userId) {
    Optional<User> user = userRepository.findByUserId(userId);

    if (!user.isPresent()) {
      throw new UserNotFoundException(userId);
    }

    return user.get();
  }

  public User createUser(CreateUserForm createUserForm) {
    User user = new User(createUserForm);

    if (isEmailRegistered(user)) {
      throw new UserEmailAlreadyRegisteredException();
    }

    user.getPhones().forEach(phone -> phone.setUser(user));
    user.setUserId(UUID.randomUUID());
    user.setToken(tokenService.createToken(user.getUserId().toString(), USER_TOKEN_EXPIRATION_TIME));

    return userRepository.save(user);
  }

  private boolean isEmailRegistered(User user) {
    ExampleMatcher emailMatcher = ExampleMatcher.matching()
        .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.ignoreCase());
    Example<User> example = Example.of(user, emailMatcher);

    return userRepository.exists(example);
  }
}
