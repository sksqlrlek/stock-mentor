package com.stockmentor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockmentor.dto.UpdateProfileRequest;
import com.stockmentor.dto.UpdateRiskTypeRequest;
import com.stockmentor.dto.UserResponse;
import com.stockmentor.entity.User;
import com.stockmentor.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    // 내 정보 조회
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyInfo(Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        return ResponseEntity.ok(new UserResponse(user));
    }

    @PutMapping("/me/risk-type")
    public ResponseEntity<String> updateRiskType(
        Authentication authentication, 
        @RequestBody UpdateRiskTypeRequest request) {
        User user = (User)authentication.getPrincipal();
        userService.updateRiskType(user.getId(), request.getRiskType());
        return ResponseEntity.ok("투자 성향이 설정되었습니다.");
    }

    @PutMapping("/me/profile")
    public ResponseEntity<String> updateProfile(
        Authentication authentication, 
        @RequestBody UpdateProfileRequest request) {
        User user = (User)authentication.getPrincipal();
        userService.updateProfile(user.getId(), request.getNickname(), request.getInterestSector(), request.getRiskType());
        return ResponseEntity.ok("프로필이 수정되었습니다.");
    }

    
}
