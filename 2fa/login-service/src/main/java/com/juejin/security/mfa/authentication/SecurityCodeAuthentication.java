package com.juejin.security.mfa.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class SecurityCodeAuthentication extends UsernamePasswordAuthenticationToken {

    public SecurityCodeAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
