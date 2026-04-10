package com.stockmentor.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stockmentor.entity.User;
import com.stockmentor.repository.UserRepository;

import lombok.RequiredArgsConstructor;

public interface UserService {

    //회원가입
    void signUp(String email, String rawPassword, String nickname);

    //로그인
    User login(String email, String rawPassword);

    User findByEmail(String email);

    // 첫 투자성향 선택
    void updateRiskType(Long userId, User.RiskType riskType);

    //프로필 수정
    void updateProfile(Long userId, String nickname, String interestSector, User.RiskType riskType);

}
