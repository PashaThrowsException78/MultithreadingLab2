package com.example.multiplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication(exclude =  {WebMvcAutoConfiguration.class })
public class MultiplicationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiplicationApplication.class, args);
    }

}
