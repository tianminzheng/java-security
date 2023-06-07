package com.juejin.security.security.service;

import com.juejin.security.repository.UserRepository;
import com.juejin.security.security.model.CustomUserDetails;
import com.juejin.security.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) {

        User user = userRepository.findUserByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("用户名不正确");
        }

        return new CustomUserDetails(user);
    }
}
