package com.stockmentor.dto;

import com.stockmentor.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateProfileRequest {
    private String nickname;
    private String interestSector;
    private User.RiskType riskType;
}