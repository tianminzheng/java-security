package com.juejin.oauth2.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juejin.oauth2.entity.OAuth2AccessToken;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OAuth2AccessTokenMapper extends BaseMapper<OAuth2AccessToken> {

    default OAuth2AccessToken selectByAccessToken(String accessToken) {
        LambdaQueryWrapper<OAuth2AccessToken> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OAuth2AccessToken::getAccessToken, accessToken);

        return selectOne(queryWrapper);
    }

    default List<OAuth2AccessToken> selectListByRefreshToken(String refreshToken) {
        LambdaQueryWrapper<OAuth2AccessToken> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OAuth2AccessToken::getRefreshToken, refreshToken);

        return selectList(queryWrapper);
    }
}
