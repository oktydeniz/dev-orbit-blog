package com.odeniz.dev.orbit.model;

import lombok.Data;

@Data
public class AuthResponse {
    String message;
    Long userId;
    String accessToken;
}

