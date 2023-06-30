package com.example.userapp.controllers;

import com.example.common.models.user.Role;
import com.example.common.models.user.User;
import com.example.common.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.common.web.UserAppUrlSchema.USER;
import static com.example.common.web.UserAppUrlSchema.USERS;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = USERS)
    public List<User> getUsers() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isAdmin = currentUser.getRole().equals(Role.ADMIN);
        var blaa = userService.list().stream()
                .filter(user -> isAdmin || user.getId().equals(currentUser.getId()))
                .collect(Collectors.toList());
        return blaa;
    }

    @PreAuthorize("hasAuthority('ADMIN') or @userService.hasId(#id)")
    @GetMapping(value = USER)
    public User getUser(@PathVariable String id) {
        return userService.findById(id).orElse(null);
    }

    @PreAuthorize("hasAuthority('ADMIN') or @userService.hasId(#id)")
    @PutMapping(value = USER)
    public User updateUser(@PathVariable String id, User user) {
        User currentUser = userService.findById(id).orElse(null);
        // Note that this is stupid and would never happen in a real life application.
        // caused by the stupidity which is thymeleaf instead of a decent front-end framework / library
        // also lacking actual validation classes that could be used in every single crud operation.
        if (currentUser != null) {
            currentUser.setEmail(user.getEmail());
            currentUser.setFirstName(null);
            currentUser.setLastName(user.getLastName());
            return userService.update(id, currentUser);
        }
        return userService.update(id, user);
    }

    @PreAuthorize("hasAuthority('ADMIN') or @userService.hasId(#id)")
    @DeleteMapping(value = USER)
    public void delete(@PathVariable String id) {
        userService.deleteById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = USER)
    public void create(@RequestBody User User) {
        User.setRandomId();
        userService.update(User.getId(), User);
    }
}
