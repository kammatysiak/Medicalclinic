package com.kammatysiak.medicalclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MedicalClinicApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalClinicApplication.class, args);
    }

}
