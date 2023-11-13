package com.security.demo.dto;

import lombok.Data;

@Data
public class jwtAuthenicatedResponse {
    private String token;
    private String refreshToken;
}
