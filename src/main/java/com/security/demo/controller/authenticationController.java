package com.security.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.demo.dto.RefreshTokenRequest;
import com.security.demo.dto.SignInRequest;
import com.security.demo.dto.SignupRequest;
import com.security.demo.dto.jwtAuthenicatedResponse;
import com.security.demo.entity.user;
import com.security.demo.services.authenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class authenticationController {
    private final authenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<user> signup(@RequestBody SignupRequest signupRequest){
        return ResponseEntity.ok(authenticationService.signup(signupRequest));
        
    }
    @PostMapping("/signin")
    public ResponseEntity<jwtAuthenicatedResponse> signin(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<jwtAuthenicatedResponse> refresh(@RequestBody RefreshTokenRequest RefreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(RefreshTokenRequest));
    }
}
