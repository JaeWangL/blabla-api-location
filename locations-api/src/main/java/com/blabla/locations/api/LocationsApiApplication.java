package com.blabla.locations.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LocationsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocationsApiApplication.class, args);
    }

}
