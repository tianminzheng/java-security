package com.juejin.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Doctor {

    private Long id;
    private String doctorCode;
    private String doctorName;
}
