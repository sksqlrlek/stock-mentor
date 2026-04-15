package com.stockmentor.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import com.stockmentor.entity.User;
import com.stockmentor.exception.BusinessException;
import com.stockmentor.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void 회원가입_성공() {
        when(userRepo.existsByEmail("test@test.com")).thenReturn(false);
        when(passwordEncoder.encode("test1234")).thenReturn("encodedPassword");

        userService.signUp("test@test.com", "test1234", "테스터");

        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void 회원가입_실패_이메일중복() {
        when(userRepo.existsByEmail("test@test.com")).thenReturn(true);

        assertThrows(BusinessException.class, () ->
            userService.signUp("test@test.com", "test1234", "테스터")
        );
    }

    @Test
    void 이메일로_유저_조회_성공() {
        User user = User.createNormal("test@test.com", "encodedPassword", "테스터");
        when(userRepo.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        User result = userService.findByEmail("test@test.com");

        assertThat(result.getEmail()).isEqualTo("test@test.com");
    }

    @Test
    void 이메일로_유저_조회_실패() {
        when(userRepo.findByEmail("test@test.com")).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () ->
            userService.findByEmail("test@test.com")
        );
    }
}