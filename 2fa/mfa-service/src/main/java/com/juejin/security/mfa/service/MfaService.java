package com.juejin.security.mfa.service;

import com.juejin.security.mfa.model.SecurityCode;
import com.juejin.security.mfa.model.UserCredential;
import com.juejin.security.mfa.repository.SecurityCodeRepository;
import com.juejin.security.mfa.repository.UserCredentialRepository;
import com.juejin.security.mfa.util.SecurityCodeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class MfaService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private SecurityCodeRepository securityCodeRepository;

    public void addUserCredential(UserCredential userCredential) {
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        userCredentialRepository.save(userCredential);
    }

    public void validateUserCredential(UserCredential userCredentialToValidate) {
    	UserCredential userCredential =
                userCredentialRepository.findUserCredentialByUsername(userCredentialToValidate.getUsername());

        if(!Objects.isNull(userCredential)) {
            if (passwordEncoder.matches(userCredentialToValidate.getPassword(), userCredential.getPassword())) {
                generateOrRefreshSecurityCode(userCredential);
            } else {
                throw new BadCredentialsException("用户名/密码错误");
            }
        } else {
            throw new BadCredentialsException("用户名/密码错误");
        }
    }

    public boolean validateSecurityCode(SecurityCode securityCodeToValidate) {
    	SecurityCode securityCode = securityCodeRepository.findSecurityCodeByUsername(securityCodeToValidate.getUsername());
        if (!Objects.isNull(securityCode)) {
            if (securityCodeToValidate.getCode().equals(securityCode.getCode())) {
                return true;
            }
        }

        return false;
    }

    private void generateOrRefreshSecurityCode(UserCredential userCredential) {
        String generatedSecurityCode = SecurityCodeUtils.generateSecurityCode();

        SecurityCode securityCode = securityCodeRepository.findSecurityCodeByUsername(userCredential.getUsername());
        if (!Objects.isNull(securityCode)) {//如果存在安全码，则刷新该安全码
        	securityCode.setCode(generatedSecurityCode);
        } else {//如果没有找到安全码，则生成并保存一个新的认证码
            SecurityCode code = new SecurityCode();
            code.setUsername(userCredential.getUsername());
            code.setCode(generatedSecurityCode);
            securityCodeRepository.save(code);
        }
    }

}
