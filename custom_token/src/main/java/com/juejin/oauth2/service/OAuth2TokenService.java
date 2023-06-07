package com.juejin.oauth2.service;


import com.juejin.oauth2.entity.OAuth2AccessToken;

import java.util.List;

public interface OAuth2TokenService {

    //创建访问令牌
    OAuth2AccessToken createAccessToken(Long userId, Integer userType, String clientId, List<String> scopes);

    //刷新访问令牌
    OAuth2AccessToken refreshAccessToken(String refreshToken, String clientId);

    //获取访问令牌
    OAuth2AccessToken getAccessToken(String accessToken);

    //校验访问令牌
    OAuth2AccessToken checkAccessToken(String accessToken);

    //移除访问令牌
    OAuth2AccessToken removeAccessToken(String accessToken);

}
