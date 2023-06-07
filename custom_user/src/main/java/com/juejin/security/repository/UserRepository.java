package com.juejin.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juejin.security.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUsername(String username);
}
