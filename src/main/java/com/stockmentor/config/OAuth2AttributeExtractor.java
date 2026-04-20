package com.stockmentor.config;

import java.util.Map;

import com.stockmentor.exception.BusinessException;
import com.stockmentor.exception.ErrorCode;

import jakarta.servlet.http.HttpServletRequest;

public class OAuth2AttributeExtractor {

    public static String extractProvider(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.substring(uri.lastIndexOf("/") + 1);
    }

    public static String extractSocialId(String provider, Map<String, Object> attributes) {
        String socialId;
        switch (provider) {
            case "kakao":
                socialId = String.valueOf(attributes.get("id"));
                break;
            case "naver":
                Map<String, Object> naverResponse = (Map<String, Object>) attributes.get("response");
                socialId = String.valueOf(naverResponse.get("id"));
                break;
            case "google":
                socialId = String.valueOf(attributes.get("sub"));
                break;
            default:
                throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
        return socialId;
    }

    public static String extractNickname(String provider, Map<String, Object> attributes) {
        String nickname;
        switch (provider) {
            case "kakao":
                Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                nickname = String.valueOf(profile.get("nickname"));
                break;
            case "naver":
                Map<String, Object> naverResponse = (Map<String, Object>) attributes.get("response");
                nickname = String.valueOf(naverResponse.get("name"));
                break;
            case "google":
                nickname = String.valueOf(attributes.get("name"));
                break;
            default:
                throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
        return nickname;
    }

    public static String extractEmail(String provider, Map<String, Object> attributes) {
        String email = null;
        switch (provider) {
            case "kakao":
                Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
                Object kakaoEmail = kakaoAccount.get("email");
                if (kakaoEmail != null) email = String.valueOf(kakaoEmail);
                break;
            case "naver":
                Map<String, Object> naverResponse = (Map<String, Object>) attributes.get("response");
                Object naverEmail = naverResponse.get("email");
                if (naverEmail != null) email = String.valueOf(naverEmail);
                break;
            case "google":
                email = String.valueOf(attributes.get("email"));
                break;
            default:
                break;
        }
        return email;
    }
}