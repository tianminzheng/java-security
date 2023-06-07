package com.juejin.security.mfa.authentication.provider;

import com.juejin.security.mfa.authentication.UserCredentialAuthentication;
import com.juejin.security.mfa.authentication.acl.MfaAcl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class UserCredentialAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MfaAcl mfaAcl;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        
        //调用认证服务完成认证
        mfaAcl.validateUserCredential(username, password);
        return new UsernamePasswordAuthenticationToken(username, password);
    }

    public boolean supports(Class<?> aClass) {
        return UserCredentialAuthentication.class.isAssignableFrom(aClass);
    }
}
