package com.stockmentor.dto;

import com.stockmentor.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {
    private String accessToken;
    private UserResponse user;

    public LoginResponse(String accessToken, User user) {
        this.accessToken = accessToken;
        this.user = new UserResponse(user);
    }
}
