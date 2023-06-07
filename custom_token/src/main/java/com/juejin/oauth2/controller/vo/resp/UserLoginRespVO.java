package com.juejin.oauth2.controller.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginRespVO {

    private Long userId;

    private String accessToken;

    private String refreshToken;

    private Date expiresTime;

}
