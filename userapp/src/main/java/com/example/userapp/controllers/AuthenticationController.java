package com.example.userapp.controllers;

import com.example.userapp.models.request.LoginRequest;
import com.example.userapp.models.request.RegistrationRequest;
import com.example.userapp.services.AuthenticationService;
import com.example.userapp.models.response.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import static com.example.common.web.BaseAppUrlSchema.LOGIN;
import static com.example.common.web.BaseAppUrlSchema.REGISTER;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(REGISTER)
    public ResponseEntity<JwtResponse> register(RegistrationRequest request) throws URISyntaxException {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.SET_COOKIE, getJWTCookieString(authenticationService.register(request).getToken()))
                .location(new URI("http://localhost:8081/api/v1/home"))
                .build();
    }

    @PostMapping(LOGIN)
    public ResponseEntity<JwtResponse> login(LoginRequest loginRequest) throws URISyntaxException {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.SET_COOKIE, getJWTCookieString(authenticationService.login(loginRequest).getToken()))
                .location(new URI("http://localhost:8081/api/v1/home"))
                .build();
    }

    private String getJWTCookieString(String token) {
        return ResponseCookie.from("JWT_TOKEN", token)
                .domain("localhost")
                .path("/")
                .maxAge(Duration.ofHours(12))
                .sameSite("Lax")
                .build()
                .toString();
    }
}