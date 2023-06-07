package com.juejin.security.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DoctorRestTemplateClient {

    @Autowired
    RestTemplate restTemplate;

    public DoctorMapper getDoctorById(Long doctorId) {
        ResponseEntity<DoctorMapper> result =
                restTemplate.exchange("http://provider-service/doctors/{doctorId}", HttpMethod.GET, null,
                        DoctorMapper.class, doctorId);

        return result.getBody();
    }
}
