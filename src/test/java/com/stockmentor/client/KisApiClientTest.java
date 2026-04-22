package com.stockmentor.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.stockmentor.client.kis.KisApiClient;

@SpringBootTest
class KisApiClientTest {

    @Autowired
    private KisApiClient kisApiClient;

    @Test
    void 종목검색_응답구조_확인() {
        Map<String, Object> response = kisApiClient.searchStock("삼성전자");

        System.out.println("=== 종목 검색 응답 ===");
        response.forEach((key, value) -> 
            System.out.println(key + " : " + value)
        );

        assertNotNull(response);
    }

    @Test
    void 종목정보_응답구조_확인() {
        Map<String, Object> response = kisApiClient.fetchStockInfo("005930");

        System.out.println("=== 종목 정보 응답 ===");
        response.forEach((key, value) -> 
            System.out.println(key + " : " + value)
        );

        assertNotNull(response);
    }

    @Test
    void 차트데이터_응답구조_확인() {
        Map<String, Object> response = kisApiClient.fetchChartData(
                "005930",
                "20250101",
                "20250421",
                "D"
        );

        System.out.println("=== 차트 데이터 응답 ===");
        response.forEach((key, value) -> 
            System.out.println(key + " : " + value)
        );

        assertNotNull(response);
    }
}