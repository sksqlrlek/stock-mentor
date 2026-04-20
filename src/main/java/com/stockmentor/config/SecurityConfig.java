package com.stockmentor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.stockmentor.repository.UserRepository;
import com.stockmentor.service.OAuth2UserServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${app.oauth2.redirect-uri.success}")
    private String successRedirectUri;

    @Value("${app.oauth2.redirect-uri.failure}")
    private String failureRedirectUri;

    @Bean
    public JwtFilter jwtFilter(JwtUtil jwtUtil, UserRepository userRepo) {
        return new JwtFilter(jwtUtil, userRepo);
    }

    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2SuccessHandler(JwtUtil jwtUtil, UserRepository userRepo) {
        return new OAuth2AuthenticationSuccessHandler(jwtUtil, userRepo, successRedirectUri);
    }

    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2FailureHandler() {
        return new OAuth2AuthenticationFailureHandler(failureRedirectUri);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, 
                                            JwtFilter jwtFilter,
                                            OAuth2UserServiceImpl oAuth2UserService,
                                            OAuth2AuthenticationSuccessHandler oAuth2SuccessHandler,
                                            OAuth2AuthenticationFailureHandler oAuth2FailureHandler
    ) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/login/oauth2/**").permitAll()
                .requestMatchers("/oauth2/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> 
                oauth2.userInfoEndpoint(userInfo -> userInfo
                    .userService(oAuth2UserService)
                )
                .successHandler(oAuth2SuccessHandler)
                .failureHandler(oAuth2FailureHandler)
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }
}
