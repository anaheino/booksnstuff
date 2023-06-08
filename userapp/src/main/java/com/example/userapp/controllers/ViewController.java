package com.example.userapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/v1/api/home")
    public String home() {
        return "home";
    }
}
