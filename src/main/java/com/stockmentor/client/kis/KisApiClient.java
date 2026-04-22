package com.stockmentor.client.kis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.stockmentor.exception.BusinessException;
import com.stockmentor.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KisApiClient {
    
    private final KisTokenManager tokenManager;

    @Value("${kis.app-key}")
    private String appKey;
    @Value("${kis.app-secret}")
    private String appSecret;
    @Value("${kis.base-url}")
    private String baseUrl;

    public KisApiClient(KisTokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    private WebClient getWebClient() {
        return WebClient.builder()
                    .baseUrl(baseUrl)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultHeader("Authorization", "Bearer " + tokenManager.getAccessToken())
                    .defaultHeader("appkey", appKey)
                    .defaultHeader("appsecret", appSecret)
                    .build();
    }

    private Map<String, Object> call(WebClient.RequestHeadersSpec<?> request) {
        return request
                .retrieve()
                .onStatus(status -> status.isError(), response ->
                    response.bodyToMono(String.class)
                        .doOnNext(body -> log.error("KIS API 오류 응답 바디: {}", body))
                        .map(body -> new BusinessException(ErrorCode.KIS_API_ERROR))
                )
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    public Map<String, Object> fetchStockInfo(String stockCode) {
        log.info("KIS 종목 정보 조회 요청 - stockCode: {}", stockCode);
        return call(getWebClient().get()
                .uri(uriBuilder -> uriBuilder
                        .path("/uapi/domestic-stock/v1/quotations/inquire-price")
                        .queryParam("FID_COND_MRKT_DIV_CODE", "J")
                        .queryParam("FID_INPUT_ISCD", stockCode)
                        .build()
                )
                .header("tr_id", "FHKST01010100"));
    }

    public Map<String, Object> fetchChartData(String stockCode, String startDate, String endDate, String periodCode) {
        log.info("KIS 차트 데이터 조회 요청 - stockCode: {}, periodCode: {}", stockCode, periodCode);
        return call(getWebClient().get()
                .uri(uriBuilder -> uriBuilder
                        .path("/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice")
                        .queryParam("FID_COND_MRKT_DIV_CODE", "J")
                        .queryParam("FID_INPUT_ISCD", stockCode)
                        .queryParam("FID_INPUT_DATE_1", startDate)
                        .queryParam("FID_INPUT_DATE_2", endDate)
                        .queryParam("FID_PERIOD_DIV_CODE", periodCode)
                        .queryParam("FID_ORG_ADJ_PRC", "0")
                        .build()
                )
                .header("tr_id", "FHKST03010100"));
    }

    public Map<String, Object> searchStock(String keyword) {
        log.info("KIS 종목 검색 요청 - keyword: {}", keyword);
        return call(getWebClient().get()
                .uri(uriBuilder -> uriBuilder
                        .path("/uapi/domestic-stock/v1/quotations/search-stock-info")
                        .queryParam("PRDT_TYPE_CD", "300")
                        .queryParam("PDNO", keyword)
                        .build()
                )
                .header("tr_id", "CTPF1002R"));
    }
}