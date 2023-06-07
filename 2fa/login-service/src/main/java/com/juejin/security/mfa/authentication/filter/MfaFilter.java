package com.juejin.security.mfa.authentication.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.juejin.security.mfa.authentication.SecurityCodeAuthentication;
import com.juejin.security.mfa.authentication.UserCredentialAuthentication;

@Component
public class MfaFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String code = request.getHeader("code");

        if (code == null) {
            Authentication userCredentialAuthentication = new UserCredentialAuthentication(username, password);
            authenticationManager.authenticate(userCredentialAuthentication);
        } else {
            Authentication securityCodeAuthentication = new SecurityCodeAuthentication(username, code);
            authenticationManager.authenticate(securityCodeAuthentication);

            response.setHeader("SecurityCode", code);
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/login");
    }
}
