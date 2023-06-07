package com.juejin.oauth2.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juejin.oauth2.entity.OAuth2Client;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OAuth2ClientMapper extends BaseMapper<OAuth2Client> {

    default List<OAuth2Client> selectList() {
        return selectList(new QueryWrapper<>());
    }

    default OAuth2Client selectByClientId(String clientId) {

        LambdaQueryWrapper<OAuth2Client> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OAuth2Client::getClientId, clientId);
        return selectOne(queryWrapper);
    }
}
