package com.security.demo.services;

import com.security.demo.dto.RefreshTokenRequest;
import com.security.demo.dto.SignInRequest;
import com.security.demo.dto.SignupRequest;
import com.security.demo.dto.jwtAuthenicatedResponse;
import com.security.demo.entity.user;

public interface authenticationService {
    user signup(SignupRequest signupRequest);
    jwtAuthenicatedResponse signin(SignInRequest signInRequest);
    jwtAuthenicatedResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
