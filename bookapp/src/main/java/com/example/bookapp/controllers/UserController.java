package com.example.bookapp.controllers;

import com.example.bookapp.models.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.common.web.UserAppUrlSchema.USERS;

@RestController
public class UserController {

    @GetMapping(value = USERS)
    public User getUsers() {
        return new User();
    }
}
