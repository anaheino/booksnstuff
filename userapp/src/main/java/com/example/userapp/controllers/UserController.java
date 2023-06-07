package com.example.userapp.controllers;

import com.example.userapp.models.User;
import com.example.userapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.common.web.UserAppUrlSchema.USER;
import static com.example.common.web.UserAppUrlSchema.USERS;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = USERS)
    public List<User> getUsers() {
        return userService.list();
    }

    @GetMapping(value = USER)
    public User getUser(@PathVariable String id) {
        return userService.findById(id).orElse(null);
    }

    @PutMapping(value = USER)
    public User updateUser(@PathVariable String id, @RequestBody User User) {
        return userService.update(id, User);
    }

    @DeleteMapping(value = USER)
    public void delete(@PathVariable String id) {
        userService.deleteById(id);
    }

    @PostMapping(value = USER)
    public void create(@RequestBody User User) {
        User.setRandomId();
        userService.update(User.getId(), User);
    }
}
