package com.stockmentor.client;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.stockmentor.dto.NewsResponse;
import com.stockmentor.exception.BusinessException;
import com.stockmentor.exception.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NaverNewsClient {
    
    private final WebClient webClient;

    public NaverNewsClient(
        @Value("${naver.search.client-id}") String clientId,
        @Value("${naver.search.client-secret}") String clientSecret,
        @Value("${naver.search.base-url}") String baseUrl
    ) {
        this.webClient = WebClient.builder()
                                .baseUrl(baseUrl)
                                .defaultHeader("X-Naver-Client-Id", clientId)
                                .defaultHeader("X-Naver-Client-Secret", clientSecret)
                                .build();
    }

    public List<NewsResponse> fetchNews(String keyword) {
        log.info("네이버 뉴스 조회 요청 - {}", keyword);

        Map<String, Object> response = webClient.get()
                                    .uri(uriBuilder -> uriBuilder
                                                    .path("/v1/search/news.json")
                                                    .queryParam("query", keyword)
                                                    .queryParam("display", 5)
                                                    .queryParam("sort", "date")
                                                    .build()  
                                    )
                                    .retrieve()
                                    .onStatus(status -> status.isError(), res ->
                                            res.bodyToMono(String.class)
                                                    .doOnNext(body -> log.error("네이버 뉴스 API 오류: {}", body))
                                                    .map(body -> new BusinessException(ErrorCode.NEWS_API_ERROR))
                                    )
                                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                                    .block();

        if (response == null || response.get("items") == null) {
            return List.of();
        }

        List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");

        return items.stream()
                    .map(item -> NewsResponse.of(
                                    cleanHtmlTag((String) item.get("title")),
                                    cleanHtmlTag((String) item.get("description")),
                                    (String) item.get("link"),
                                    (String) item.get("pubDate")
                    ))
                    .toList();
    }


    private String cleanHtmlTag(String text) {
        if (text == null) return "";
        return text.replaceAll("<[^>]*>", "");
    }
}
