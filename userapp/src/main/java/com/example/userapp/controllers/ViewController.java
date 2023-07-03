package com.example.userapp.controllers;

import com.example.common.models.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.example.common.web.BaseAppUrlSchema.HOME;
import static com.example.common.web.BaseAppUrlSchema.LOGIN_FORM;
import static com.example.common.web.UserAppUrlSchema.USER_PAGE;
import static com.example.common.web.UserAppUrlSchema.USER_STUPID_MAPPING;

@Controller
@AllArgsConstructor
public class ViewController {

    private final UserController userController;

    @GetMapping(LOGIN_FORM)
    public String login() {
        return "login";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(HOME)
    public String home() {
        return "home";
    }

    @GetMapping(USER_PAGE)
    public String userPage(Model model) {
        List<User> users = userController.getUsers();
        model.addAttribute("users", users);
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "user-page";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping(USER_STUPID_MAPPING)
    public ResponseEntity<String> userPageStupidPut(@PathVariable String id, @ModelAttribute("formData") User user) throws URISyntaxException {
        // In real life this shit should not exist.
        // instead we'd validate the object on userController side,
        // and add JWT_TOKEN regeneration to recreate the token with updated user data.
        // note that you have to log out for the user update to reflect in the JWT token, because this is a stupid poc.
        User previousUser = userController.getUser(id);
        previousUser.setFirstName(user.getFirstName());
        previousUser.setLastName(user.getLastName());
        previousUser.setEmail(user.getEmail());
        userController.updateUserThymeleaf(id, previousUser);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(new URI("http://localhost:8081/v1/api/logout"))
                .build();
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping(USER_STUPID_MAPPING)
    public ResponseEntity<String> userPageStupidDelete(@PathVariable String id, @ModelAttribute("formData") User user) throws URISyntaxException {
        // In real life this shit should not exist either.
        userController.delete(id);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(new URI("http://localhost:8081/v1/api/logout"))
                .build();
    }
}
