package com.stockmentor.service;

import com.stockmentor.entity.User;

public interface UserService {

    //회원가입
    void signUp(String email, String rawPassword, String nickname);

    //로그인
    User login(String email, String rawPassword);

    User findByEmail(String email);

    User findById(Long userId);
    
    // 첫 투자성향 선택
    void updateRiskType(Long userId, User.RiskType riskType);

    //프로필 수정
    void updateProfile(Long userId, String nickname, String interestSector, User.RiskType riskType);

}
