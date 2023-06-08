package com.example.bookapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class BookApp {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(BookApp.class, args);
    }
}
