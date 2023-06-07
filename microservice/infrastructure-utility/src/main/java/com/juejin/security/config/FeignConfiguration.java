package com.juejin.security.config;

import feign.Logger;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"com.juejin.security.*"})
public class FeignConfiguration {


    @Bean
    Logger.Level feignLoggerlevel() {
        return Logger.Level.FULL;
    }


    @Bean
    FeignErrorDecoder errorDecoder(){
        return new FeignErrorDecoder();
    }
}
