package com.example.bookapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = BookApp.class)
class BookAppTests {

    @Test
    void contextLoads() {
    }

}
