package com.juejin.security.mfa.config;

import com.juejin.security.mfa.authentication.filter.MfaFilter;
import com.juejin.security.mfa.authentication.provider.SecurityCodeAuthenticationProvider;
import com.juejin.security.mfa.authentication.provider.UserCredentialAuthenticationProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MfaFilter mfaFilter;

    @Autowired
    private SecurityCodeAuthenticationProvider securityCodeAuthenticationProvider;

    @Autowired
    private UserCredentialAuthenticationProvider userCredentialAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(securityCodeAuthenticationProvider)
            .authenticationProvider(userCredentialAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.addFilterAt(
        		mfaFilter,
                BasicAuthenticationFilter.class);

        http.authorizeRequests()
                .anyRequest().authenticated();
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
