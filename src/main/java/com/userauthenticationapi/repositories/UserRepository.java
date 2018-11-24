package com.userauthenticationapi.repositories;

import com.userauthenticationapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  @Query(value = "SELECT * FROM user u where u.user_id = :userId", nativeQuery = true)
  Optional<User> findByUserId(@Param("userId") UUID userId);
}
