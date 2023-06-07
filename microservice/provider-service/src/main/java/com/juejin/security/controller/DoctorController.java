package com.juejin.security.controller;

import com.juejin.security.domain.Doctor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="doctors")
public class DoctorController {

    @GetMapping(value="/{doctorId}")
    public Doctor getDoctorById(@PathVariable("doctorId") Long doctorId) {
        return new Doctor(doctorId, "DemoCode", "DemoName");
    }

    @GetMapping(value="/mock")
    public String getDoctorById() {
        return "Mock Doctor";
    }
}
