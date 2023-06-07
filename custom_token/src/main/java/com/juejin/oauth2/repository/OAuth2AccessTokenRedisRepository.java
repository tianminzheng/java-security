package com.juejin.oauth2.repository;

import com.juejin.oauth2.entity.OAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OAuth2AccessTokenRedisRepository {

    @Autowired
    private RedisTemplate<String, OAuth2AccessToken> redisTemplate;

    private static final String HASH_NAME = "oauth2_access_token:";

    public OAuth2AccessToken get(String accessToken) {
        return redisTemplate.opsForValue().get(HASH_NAME + accessToken);
    }

    public void set(OAuth2AccessToken accessToken) {
        redisTemplate.opsForValue().set(HASH_NAME + accessToken.getAccessToken(), accessToken);
    }

    public void delete(String accessToken) {
        redisTemplate.delete(HASH_NAME + accessToken);
    }
}
