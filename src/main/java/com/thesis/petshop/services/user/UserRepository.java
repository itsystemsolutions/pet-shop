package com.thesis.petshop.services.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String name);

    Optional<User> findByEmail(String email);

    List<User> findAllByType(String type);
}
