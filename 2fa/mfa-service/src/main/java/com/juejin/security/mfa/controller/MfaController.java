package com.juejin.security.mfa.controller;

import com.juejin.security.mfa.model.SecurityCode;
import com.juejin.security.mfa.model.UserCredential;
import com.juejin.security.mfa.service.MfaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class MfaController {

    @Autowired
    private MfaService mfaService;

    //添加UserCredential
    @PostMapping("/userCredential/add")
    public void addUserCredential(@RequestBody UserCredential userCredential) {
    	mfaService.addUserCredential(userCredential);
    }

    //通过用户名/密码对用户进行首次认证
    @PostMapping("/userCredential/validate")
    public void validateUserCredential(@RequestBody UserCredential userCredential) {
    	mfaService.validateUserCredential(userCredential);
    }

    //通过用户名/安全码进行二次认证
    @PostMapping("/securityCode/validate")
    public void validateSecurityCode(@RequestBody SecurityCode securityCode, HttpServletResponse response) {
        if (mfaService.validateSecurityCode(securityCode)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
