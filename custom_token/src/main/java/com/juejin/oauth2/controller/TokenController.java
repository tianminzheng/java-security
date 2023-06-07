package com.juejin.oauth2.controller;

import com.juejin.oauth2.converter.AccessTokenConverter;
import com.juejin.oauth2.controller.vo.resp.UserLoginRespVO;
import com.juejin.oauth2.entity.OAuth2AccessToken;
import com.juejin.oauth2.service.OAuth2TokenService;
import com.juejin.oauth2.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private OAuth2TokenService oauth2TokenService;

    @PostMapping("/refresh")
    public Result<UserLoginRespVO> refreshToken(@RequestParam("refreshToken") String refreshToken, @RequestParam("clientId") String clientId) {

        OAuth2AccessToken token = oauth2TokenService.refreshAccessToken(refreshToken, clientId);
        return Result.success(AccessTokenConverter.INSTANCE.convert(token));
    }
}
