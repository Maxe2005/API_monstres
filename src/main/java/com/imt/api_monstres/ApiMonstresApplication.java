package com.imt.api_monstres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class ApiMonstresApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiMonstresApplication.class, args);
    }

}
