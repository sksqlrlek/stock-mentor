package com.stockmentor.dto;

import com.stockmentor.entity.User;

import lombok.Getter;

@Getter
public class UserResponse {
    private Long id;
    private String email;
    private String nickname;
    private User.RiskType riskType;
    private String interestSector;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.riskType = user.getRiskType();
        this.interestSector = user.getInterestSector();
    }
}
