package com.juejin.security.controller;

import com.juejin.security.client.DoctorFeignClient;
import com.juejin.security.client.DoctorMapper;
import com.juejin.security.client.DoctorRestTemplateClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="integration")
public class DoctorIntegrationController {

    @Autowired
    DoctorRestTemplateClient doctorRestTemplateClient;

    @Autowired
    DoctorFeignClient doctorFeignClient;

    @GetMapping(value="/{doctorId}")
    public DoctorMapper getDoctor(@PathVariable("doctorId") Long doctorId) {
        return doctorRestTemplateClient.getDoctorById(doctorId);
    }

    @GetMapping(value="/mock")
    public String getMockDoctor() {
        return doctorFeignClient.mock();
    }
}
