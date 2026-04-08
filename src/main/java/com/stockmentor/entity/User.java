package com.stockmentor.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "USERS_SEQ", allocationSize = 1)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "EMAIL", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "PASSWORD", length = 255)
    private String password;

    @Column(name = "NICKNAME", nullable = false, length = 50)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "RISK_TYPE", length = 20)
    private RiskType riskType;

    @Column(name = "INTEREST_SECTOR", length = 100)
    private String interestSector;

    @Column(name = "SOCIAL_PROVIDER", length = 20)
    private String socialProvider;

    @Column(name = "SOCIAL_ID", length = 100)
    private String socialId;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // 일반 회원가입
    public static User createNormal(String email, String encodedPassword, String nickname) {
        User user = new User();
        user.email = email;
        user.password = encodedPassword;
        user.nickname = nickname;
        return user;
    }

    // 소셜 회원가입
    public static User createSocial(String email, String nickname, String socialProvider, String socialId) {
        User user = new User();
        user.email = email;
        user.nickname = nickname;
        user.socialProvider = socialProvider;
        user.socialId = socialId;
        return user;
    }

    // 첫 로그인 때 투자 성향 선택
    public void updateRiskType(RiskType riskType) {
        this.riskType = riskType;
    }

    // 프로필 수정
    public void updateProfile(String nickname, String interestSector, RiskType riskType) {
        if (nickname != null) this.nickname = nickname;
        if (interestSector != null) this.interestSector = interestSector;
        if (riskType != null) this.riskType = riskType;
    }

    public enum RiskType {
        AGGRESSIVE, NEUTRAL, CONSERVATIVE
    }
}