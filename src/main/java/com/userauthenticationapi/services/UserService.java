package com.userauthenticationapi.services;

import com.userauthenticationapi.exceptions.InvalidUserCredentialsException;
import com.userauthenticationapi.exceptions.UserEmailAlreadyRegisteredException;
import com.userauthenticationapi.exceptions.UserNotFoundException;
import com.userauthenticationapi.models.User;
import com.userauthenticationapi.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.Date;
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

  public User save(User user) {
    if (findByEmail(user.getEmail()).isPresent()) {
      throw new UserEmailAlreadyRegisteredException();
    }

    user.getPhones().forEach(phone -> phone.setUser(user));
    user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
    user.setUserId(UUID.randomUUID());
    user.setToken(generateToken(user));

    return userRepository.save(user);
  }

  public User authenticate(String email, String password) {
    Optional<User> optionalUser = findByEmail(email);
    if (!optionalUser.isPresent()) {
      throw new InvalidUserCredentialsException();
    }

    User user = optionalUser.get();
    if (!BCrypt.checkpw(password, user.getPassword())) {
      throw new InvalidUserCredentialsException();
    }

    user.setLastLogin(new Date(System.currentTimeMillis()));
    user.setToken(generateToken(user));

    return userRepository.save(user);
  }

  private String generateToken(User user) {
    return tokenService.createToken(user.getUserId().toString(), USER_TOKEN_EXPIRATION_TIME);
  }

  private Optional<User> findByEmail(String email) {
    User probe = new User();
    probe.setEmail(email);
    ExampleMatcher emailMatcher = ExampleMatcher.matching()
        .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.ignoreCase());
    Example<User> example = Example.of(probe, emailMatcher);

    return userRepository.findOne(example);
  }
}
