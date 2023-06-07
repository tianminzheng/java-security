package com.juejin.oauth2.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.juejin.oauth2.entity.ClientStatus;
import com.juejin.oauth2.entity.OAuth2Client;
import com.juejin.oauth2.exception.ServiceException;
import com.juejin.oauth2.mapper.OAuth2ClientMapper;
import com.juejin.oauth2.service.OAuth2ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

@Service
@Validated
@Slf4j
public class OAuth2ClientServiceImpl implements OAuth2ClientService {

    @Resource
    private OAuth2ClientMapper oauth2ClientMapper;

    @Override
    public OAuth2Client getOAuth2Client(Long id) {
        return oauth2ClientMapper.selectById(id);
    }


    @Override
    public OAuth2Client validOAuthClientFromCache(String clientId) throws ServiceException {
        // 校验客户端状态
        OAuth2Client client = oauth2ClientMapper.selectByClientId(clientId);
        if (client == null) {
            throw new ServiceException("OAuth2 客户端不存在");
        }
        if (ObjectUtil.notEqual(client.getStatus(), ClientStatus.ENABLE.getStatus())) {
            throw new ServiceException("OAuth2 客户端已被禁用");
        }

        return client;
    }

}
