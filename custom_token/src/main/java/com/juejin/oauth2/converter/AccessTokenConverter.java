package com.juejin.oauth2.converter;

import com.juejin.oauth2.controller.vo.resp.UserLoginRespVO;
import com.juejin.oauth2.entity.OAuth2AccessToken;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccessTokenConverter {

    AccessTokenConverter INSTANCE = Mappers.getMapper(AccessTokenConverter.class);

    //Entity->VO
    UserLoginRespVO convert(OAuth2AccessToken entity);
}
