package com.juejin.security.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "provider-service", path = "/doctors")
public interface DoctorFeignClient {

    @RequestMapping("/mock")
    String mock();
}