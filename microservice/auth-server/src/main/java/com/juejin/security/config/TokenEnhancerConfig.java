package com.juejin.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

@Configuration
public class TokenEnhancerConfig {

	@Bean
	public TokenEnhancer jwtTokenEnhancer() {
		return new JWTTokenEnhancer();
	}
}
