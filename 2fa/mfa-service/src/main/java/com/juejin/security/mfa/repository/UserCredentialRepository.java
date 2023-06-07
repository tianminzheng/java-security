package com.juejin.security.mfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juejin.security.mfa.model.UserCredential;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer> {

	UserCredential findUserCredentialByUsername(String username);
}
