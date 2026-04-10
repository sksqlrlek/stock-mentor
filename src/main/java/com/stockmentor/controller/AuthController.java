package com.stockmentor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockmentor.config.JwtUtil;
import com.stockmentor.dto.LoginRequest;
import com.stockmentor.dto.LoginResponse;
import com.stockmentor.dto.SignupRequest;
import com.stockmentor.entity.User;
import com.stockmentor.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        userService.signUp(request.getEmail(), request.getPassword(), request.getNickname());
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        User user = userService.login(request.getEmail(), request.getPassword());
        String accessToken = jwtUtil.generateAccessToken(user.getId());
        return ResponseEntity.ok(new LoginResponse(accessToken, user));
    }
}
