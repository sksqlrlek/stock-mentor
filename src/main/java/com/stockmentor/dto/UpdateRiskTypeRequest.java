package com.stockmentor.dto;

import com.stockmentor.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateRiskTypeRequest {
    private User.RiskType riskType;
}
