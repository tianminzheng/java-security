package com.juejin.oauth2.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juejin.oauth2.entity.OAuth2RefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OAuth2RefreshTokenMapper extends BaseMapper<OAuth2RefreshToken> {

    default int deleteByRefreshToken(String refreshToken) {
        LambdaQueryWrapper<OAuth2RefreshToken> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OAuth2RefreshToken::getRefreshToken, refreshToken);

        return delete(queryWrapper);
    }

    default OAuth2RefreshToken selectByRefreshToken(String refreshToken) {
        LambdaQueryWrapper<OAuth2RefreshToken> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OAuth2RefreshToken::getRefreshToken, refreshToken);

        return selectOne(queryWrapper);
    }
}
