package ru.nsu.fit.directors.businessservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@OpenAPIDefinition
public class BusinessServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessServiceApplication.class, args);
    }

}
