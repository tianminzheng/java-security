package com.juejin.oauth2.service;

import com.juejin.oauth2.entity.OAuth2AccessToken;
import com.juejin.oauth2.entity.User;

public interface UserAuthService {

    User authenticate(String username, String password);

    //账号登录
    OAuth2AccessToken login(String username, String password, String code);

    //基于令牌退出登录
    void logout(String token);
}
