package com.juejin.oauth2.service;

import com.juejin.oauth2.entity.OAuth2Client;
import com.juejin.oauth2.exception.ServiceException;

public interface OAuth2ClientService {

    OAuth2Client getOAuth2Client(Long id);

    OAuth2Client validOAuthClientFromCache(String clientId) throws ServiceException;

}
