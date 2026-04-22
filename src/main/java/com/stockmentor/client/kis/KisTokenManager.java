package com.stockmentor.client.kis;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.stockmentor.client.kis.dto.KisTokenResponse;
import com.stockmentor.exception.BusinessException;
import com.stockmentor.exception.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KisTokenManager {
    private final String appKey;
    private final String appSecret;
    private final String baseUrl;
    private final WebClient webClient;

    private String accessToken;
    private LocalDateTime tokenExpiredAt;

    //생성자
    public KisTokenManager(
        @Value("${kis.app-key}") String appKey,
        @Value("${kis.app-secret}") String appSecret,
        @Value("${kis.base-url}") String baseUrl
    ) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.baseUrl = baseUrl;
        this.webClient = WebClient.builder()
                                .baseUrl(baseUrl)
                                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .build();
    }

    public String getAccessToken() {
        if(accessToken == null || isTokenExpired()) {
            log.info("KIS Access Token 발급 요청");
            issueToken();
        }
        log.info("사용 중인 토큰 앞 20자: {}", accessToken.substring(0, 20));
        return accessToken;
    }

    private void issueToken() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("grant_type", "client_credentials");
        requestBody.put("appkey", appKey);
        requestBody.put("appsecret", appSecret);

        KisTokenResponse response = webClient.post()
                                        .uri("/oauth2/tokenP")
                                        .bodyValue(requestBody)
                                        .retrieve()
                                        .bodyToMono(KisTokenResponse.class)
                                        .block();

        if(response == null || response.getAccessToken() == null) {
            throw new BusinessException(ErrorCode.KIS_TOKEN_ERROR);
        }

        this.accessToken = response.getAccessToken();
        this.tokenExpiredAt = LocalDateTime.now().plusSeconds(response.getExpiresIn() - 60);
        log.info("KIS Access Token 발급 완료. 만료 시간 : {}", tokenExpiredAt);
    }

    private boolean isTokenExpired() {
        return tokenExpiredAt == null || LocalDateTime.now().isAfter(tokenExpiredAt);
    }
}
