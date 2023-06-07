package com.juejin.oauth2.controller;

import cn.hutool.core.util.StrUtil;
import com.juejin.oauth2.converter.AccessTokenConverter;
import com.juejin.oauth2.controller.vo.req.UserLoginReqVO;
import com.juejin.oauth2.controller.vo.resp.UserLoginRespVO;
import com.juejin.oauth2.entity.OAuth2AccessToken;
import com.juejin.oauth2.service.UserAuthService;
import com.juejin.oauth2.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserAuthController {

    @Autowired
    private UserAuthService authService;

    private static String AUTH_HEADER = "Authorization";

    @PostMapping("/login")
    public Result<UserLoginRespVO> login(@RequestBody @Valid UserLoginReqVO reqVO) {

        OAuth2AccessToken token = authService.login(reqVO.getUsername(), reqVO.getPassword(), reqVO.getCode());
        return Result.success(AccessTokenConverter.INSTANCE.convert(token));
    }

    @PostMapping("/logout")
    public Result<Boolean> logout(HttpServletRequest request) {
        String token = request.getHeader(AUTH_HEADER);
        if (StrUtil.isNotBlank(token)) {
            authService.logout(token);
        }
        return Result.success(true);
    }
}
