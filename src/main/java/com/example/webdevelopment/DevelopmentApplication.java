package com.example.webdevelopment;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.TimeZone;

@SpringBootApplication
@EnableMethodSecurity
public class DevelopmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevelopmentApplication.class, args);
    }
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}


