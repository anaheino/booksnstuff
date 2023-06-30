package com.example.userapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@ComponentScan({
        "com.example.common.services",
        "com.example.common.repositories",
        "com.example.common.security",
        "com.example.userapp.config",
        "com.example.userapp.controllers",
        "com.example.userapp.services"
})
@EnableJpaRepositories({"com.example.common.repositories"})
@EntityScan({"com.example.common.models"})
public class UserApp {

    public static void main(String[] args) {
        SpringApplication.run(UserApp.class, args);
    }

}
