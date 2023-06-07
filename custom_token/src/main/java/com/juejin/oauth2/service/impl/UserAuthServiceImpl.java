package com.juejin.oauth2.service.impl;

import com.juejin.oauth2.constant.OAuth2ClientConstant;
import com.juejin.oauth2.entity.OAuth2AccessToken;
import com.juejin.oauth2.entity.User;
import com.juejin.oauth2.service.OAuth2TokenService;
import com.juejin.oauth2.service.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private OAuth2TokenService oauth2TokenService;

    private static final Integer USER_TYPE = 1;

    @Override
    public User authenticate(String username, String password) {

        //省略用户认证环节，模拟生产一个默认用户
        User user = new User();
        user.setId(10000L);
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname("nickname");
        user.setRemark("remark");

        return user;
    }

    @Override
    public OAuth2AccessToken login(String username, String password, String code) {

        //验证授权码是否正确

        //执行认证
        User user = authenticate(username, password);

        // 创建令牌
        return createTokenAfterLoginSuccess(user.getId(), username);
    }


    private OAuth2AccessToken createTokenAfterLoginSuccess(Long userId, String username) {
        // 创建访问令牌
        OAuth2AccessToken oauth2AccessToken = oauth2TokenService.createAccessToken(userId, USER_TYPE,
                OAuth2ClientConstant.CLIENT_ID_DEFAULT, null);

        return oauth2AccessToken;
    }

    @Override
    public void logout(String token) {
        // 删除访问令牌
        OAuth2AccessToken oauth2AccessToken = oauth2TokenService.removeAccessToken(token);
    }
}
