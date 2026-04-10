package com.stockmentor.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stockmentor.entity.User;
import com.stockmentor.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Override
    public void signUp(String email, String rawPassword, String nickname) {
        if(userRepo.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        String encodeedPassword = passwordEncoder.encode(rawPassword);
        User user = User.createNormal(email, encodeedPassword, nickname);
        userRepo.save(user);
    }

    //로그인
    @Override
    public User login(String email, String rawPassword) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("이메일 혹은 비밀번호가 일치하지 않습니다."));

        if(!passwordEncoder.matches(rawPassword, user.getPassword())) throw new IllegalArgumentException("이메일 혹은 비밀번호가 일치하지 않습니다.");

        return user;
    }

    @Override
    public User findByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    // 첫 투자성향 선택
    @Override
    public void updateRiskType(Long userId, User.RiskType riskType) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        user.updateRiskType(riskType);
    }

    //프로필 수정
    @Override
    public void updateProfile(Long userId, String nickname, String interestSector, User.RiskType riskType) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        user.updateProfile(nickname, interestSector, riskType);
    }
}
