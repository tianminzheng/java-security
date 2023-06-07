package com.juejin.security.mfa.authentication.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MfaAcl {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${mfa.service.url}")
    private String mfaServiceUrl;

    public void validateUserCredential(String username, String password) {
        String url = mfaServiceUrl + "/userCredential/validate";

        MfaDto body = new MfaDto();
        body.setUsername(username);
        body.setPassword(password);

        HttpEntity<MfaDto> request = new HttpEntity<MfaDto>(body);

        restTemplate.postForEntity(url, request, Void.class);
    }

    public boolean validateSecurityCode(String username, String code) {
        String url = mfaServiceUrl + "/securityCode/validate";

        MfaDto body = new MfaDto();
        body.setUsername(username);
        body.setCode(code);

        HttpEntity<MfaDto> request = new HttpEntity<MfaDto>(body);
        
        ResponseEntity<Void> response = restTemplate.postForEntity(url, request, Void.class);

        return response.getStatusCode().equals(HttpStatus.OK);
    }
}
