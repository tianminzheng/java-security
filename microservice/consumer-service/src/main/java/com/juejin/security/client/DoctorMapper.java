package com.juejin.security.client;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class DoctorMapper implements Serializable {

    private Long id;
    private String doctorCode;
    private String doctorName;

    public DoctorMapper() {
    }
}
