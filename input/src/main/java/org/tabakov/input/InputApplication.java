package org.tabakov.input;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication(exclude =  {WebMvcAutoConfiguration.class })
public class InputApplication {

    public static void main(String[] args) {
        SpringApplication.run(InputApplication.class, args);
    }

}
