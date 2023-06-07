package com.juejin.security.mfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juejin.security.mfa.model.SecurityCode;

import java.util.Optional;

public interface SecurityCodeRepository extends JpaRepository<SecurityCode, Integer> {

	SecurityCode findSecurityCodeByUsername(String username);
}
