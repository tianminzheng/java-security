package com.juejin.oauth2.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.juejin.oauth2.entity.OAuth2AccessToken;
import com.juejin.oauth2.entity.OAuth2Client;
import com.juejin.oauth2.entity.OAuth2RefreshToken;
import com.juejin.oauth2.exception.ServiceException;
import com.juejin.oauth2.mapper.OAuth2AccessTokenMapper;
import com.juejin.oauth2.mapper.OAuth2RefreshTokenMapper;
import com.juejin.oauth2.service.OAuth2ClientService;
import com.juejin.oauth2.service.OAuth2TokenService;
import com.juejin.oauth2.repository.OAuth2AccessTokenRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class OAuth2TokenServiceImpl implements OAuth2TokenService {

    @Autowired
    private OAuth2AccessTokenMapper oauth2AccessTokenMapper;
    @Autowired
    private OAuth2RefreshTokenMapper oauth2RefreshTokenMapper;
    @Autowired
    private OAuth2ClientService oauth2ClientService;

    @Autowired
    private OAuth2AccessTokenRedisRepository oauth2AccessTokenRedisRepository;

    @Override
    @Transactional
    public OAuth2AccessToken createAccessToken(Long userId, Integer userType, String clientId, List<String> scopes) {

        //获取客户端信息
        OAuth2Client client = oauth2ClientService.validOAuthClientFromCache(clientId);

        // 创建刷新令牌
        OAuth2RefreshToken refreshToken = createOAuth2RefreshToken(userId, userType, client, scopes);

        // 创建访问令牌
        return createOAuth2AccessToken(refreshToken, client);
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(String refreshToken, String clientId) {
        // 查询访问令牌
        OAuth2RefreshToken oauth2RefreshToken = oauth2RefreshTokenMapper.selectByRefreshToken(refreshToken);
        if (oauth2RefreshToken == null) {
            throw new ServiceException("无效的刷新令牌");
        }

        // 校验 Client 匹配
        OAuth2Client client = oauth2ClientService.validOAuthClientFromCache(clientId);
        if (ObjectUtil.notEqual(clientId, oauth2RefreshToken.getClientId())) {
            throw new ServiceException("刷新令牌的客户端编号不正确");
        }
        // 移除相关的访问令牌
        List<OAuth2AccessToken> accessTokens = oauth2AccessTokenMapper.selectListByRefreshToken(refreshToken);
        if (CollUtil.isNotEmpty(accessTokens)) {
            for(OAuth2AccessToken accessToken : accessTokens ) {
                oauth2AccessTokenMapper.deleteById(accessToken.getId());
                oauth2AccessTokenRedisRepository.delete(accessToken.getAccessToken());
            }
        }

//         已过期的情况下，删除刷新令牌
        if (isExpired(oauth2RefreshToken.getExpiresTime())) {
            oauth2RefreshTokenMapper.deleteById(oauth2RefreshToken.getId());
            throw new ServiceException("刷新令牌已过期");
        }

        // 创建访问令牌
        return createOAuth2AccessToken(oauth2RefreshToken, client);
    }

    @Override
    public OAuth2AccessToken getAccessToken(String accessToken) {
        // 优先从缓存中获取
        OAuth2AccessToken oauth2AccessToken = oauth2AccessTokenRedisRepository.get(accessToken);
        if (oauth2AccessToken != null) {
            return oauth2AccessToken;
        }

        // 缓存未命中则中数据库获取
        oauth2AccessToken = oauth2AccessTokenMapper.selectByAccessToken(accessToken);

        // 更新缓存
        if (oauth2AccessToken != null && !isExpired(oauth2AccessToken.getExpiresTime())) {
            oauth2AccessTokenRedisRepository.set(oauth2AccessToken);
        }
        return oauth2AccessToken;
    }

    @Override
    public OAuth2AccessToken checkAccessToken(String accessToken) {
        OAuth2AccessToken oauth2AccessToken = getAccessToken(accessToken);
        if (accessToken == null) {
            throw new ServiceException("访问令牌不存在");
        }
        if (isExpired(oauth2AccessToken.getExpiresTime())) {
            throw new ServiceException("访问令牌已过期");
        }
        return oauth2AccessToken;
    }

    @Override
    public OAuth2AccessToken removeAccessToken(String accessToken) {
        // 删除访问令牌
        OAuth2AccessToken oauth2AccessToken = oauth2AccessTokenMapper.selectByAccessToken(accessToken);
        if (accessToken == null) {
            return null;
        }
        oauth2AccessTokenMapper.deleteById(oauth2AccessToken.getId());
        oauth2AccessTokenRedisRepository.delete(accessToken);

        // 删除刷新令牌
        oauth2RefreshTokenMapper.deleteByRefreshToken(oauth2AccessToken.getRefreshToken());
        return oauth2AccessToken;
    }

    private OAuth2AccessToken createOAuth2AccessToken(OAuth2RefreshToken refreshToken, OAuth2Client client) {
        OAuth2AccessToken accessToken = new OAuth2AccessToken()
                .setAccessToken(generateAccessToken())
                .setUserId(refreshToken.getUserId())
                .setUserType(refreshToken.getUserType())
                .setClientId(client.getClientId())
                .setScopes(refreshToken.getScopes())
                .setRefreshToken(refreshToken.getRefreshToken())
                .setExpiresTime(LocalDateTime.now().plusSeconds(client.getAccessTokenValiditySeconds()));
        oauth2AccessTokenMapper.insert(accessToken);

        // 记录到 Redis 中
        oauth2AccessTokenRedisRepository.set(accessToken);
        return accessToken;
    }

    private OAuth2RefreshToken createOAuth2RefreshToken(Long userId, Integer userType, OAuth2Client client, List<String> scopes) {
        OAuth2RefreshToken refreshToken = new OAuth2RefreshToken().setRefreshToken(generateRefreshToken())
                .setUserId(userId).setUserType(userType)
                .setClientId(client.getClientId())
                .setScopes(scopes)
                .setExpiresTime(LocalDateTime.now().plusSeconds(client.getRefreshTokenValiditySeconds()));
        oauth2RefreshTokenMapper.insert(refreshToken);
        return refreshToken;
    }

    private static String generateAccessToken() {
        return IdUtil.fastSimpleUUID();
    }

    private static String generateRefreshToken() {
        return IdUtil.fastSimpleUUID();
    }

    public static boolean isExpired(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(time);
    }
}
