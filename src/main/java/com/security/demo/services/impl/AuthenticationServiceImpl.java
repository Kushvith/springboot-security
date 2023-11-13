package com.security.demo.services.impl;


import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.demo.dto.RefreshTokenRequest;
import com.security.demo.dto.SignInRequest;
import com.security.demo.dto.SignupRequest;
import com.security.demo.dto.jwtAuthenicatedResponse;
import com.security.demo.entity.Role;
import com.security.demo.entity.user;
import com.security.demo.repository.UserRespository;
import com.security.demo.services.JWTService;
import com.security.demo.services.authenticationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements authenticationService{
    private final UserRespository userRespository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    public user signup(SignupRequest signupRequest){
        user user = new user();
        user.setEmail(signupRequest.getEmail());
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        return userRespository.save(user);

    }
    public jwtAuthenicatedResponse signin(SignInRequest signInRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        var user = userRespository.findByEmail(signInRequest.getEmail()).orElseThrow(()-> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);
        jwtAuthenicatedResponse jwtAuthenicatedResponse = new jwtAuthenicatedResponse();
        jwtAuthenicatedResponse.setToken(jwt);
        jwtAuthenicatedResponse.setRefreshToken(refreshToken);
        return jwtAuthenicatedResponse;
    }
    public jwtAuthenicatedResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
        user useremail = userRespository.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), useremail)){
            var jwt = jwtService.generateToken(useremail);
            jwtAuthenicatedResponse jwtAuthenicatedResponse = new jwtAuthenicatedResponse();
            jwtAuthenicatedResponse.setToken(jwt);
            jwtAuthenicatedResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenicatedResponse;
        }
        return null;
    }
}
