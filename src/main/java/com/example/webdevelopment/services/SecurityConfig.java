// src/main/java/com/example/menu/SecurityConfig.java
package com.example.webdevelopment.services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // Temporarily disable CSRF for testing; reconsider for production
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/audit/").permitAll()  // Open access to this endpoint
                        .requestMatchers("/protected/").authenticated()  // Require authentication for this endpoint
                        .anyRequest().authenticated()  // Require authentication for any other requests
                )
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(withDefaults()));

        return http.build();
    }



}