package com.stockmentor.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stockmentor.entity.User;
import com.stockmentor.exception.BusinessException;
import com.stockmentor.exception.ErrorCode;
import com.stockmentor.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Override
    public void signUp(String email, String rawPassword, String nickname) {
        if(userRepo.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        String encodeedPassword = passwordEncoder.encode(rawPassword);
        User user = User.createNormal(email, encodeedPassword, nickname);
        userRepo.save(user);
    }

    //로그인
    @Override
    public User login(String email, String rawPassword) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));

        if(!passwordEncoder.matches(rawPassword, user.getPassword())) throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);

        return user;
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    // 첫 투자성향 선택
    @Override
    public void updateRiskType(Long userId, User.RiskType riskType) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user.updateRiskType(riskType);
    }

    //프로필 수정
    @Override
    public void updateProfile(Long userId, String nickname, String interestSector, User.RiskType riskType) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user.updateProfile(nickname, interestSector, riskType);
    }
}
