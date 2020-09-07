package com.example.restservicejpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class RestServiceJpaApp {

    public static void main(String[] args) {
        SpringApplication.run(RestServiceJpaApp.class);
    }
}
