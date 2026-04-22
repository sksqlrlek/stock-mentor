package com.stockmentor.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class StockSearchResponse {

    private String stockCode;
    private String stockName;
    private String market;

    public static StockSearchResponse of(String stockCode, String stockName, String market) {
        return StockSearchResponse.builder()
                .stockCode(stockCode)
                .stockName(stockName)
                .market(market)
                .build();
    }
}