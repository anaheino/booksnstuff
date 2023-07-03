package com.example.bookapp;

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
        "com.example.bookapp.config",
        "com.example.bookapp.controllers",
        "com.example.bookapp.services"
})
@EnableJpaRepositories({"com.example.common.repositories", "com.example.bookapp.repositories"})
@EntityScan({"com.example.common.models", "com.example.bookapp.models"})
public class BookApp {

    public static void main(String[] args) {
        SpringApplication.run(BookApp.class, args);
    }
}
