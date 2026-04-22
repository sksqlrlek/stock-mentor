package com.stockmentor.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class StockInfoResponse {

    private String stockCode;
    private String stockName;
    private String currentPrice;
    private String priceChange;
    private String priceChangeRate;
    private String marketCap;
    private String per;
    private String pbr;
    private String high52Week;
    private String low52Week;

    public static StockInfoResponse of(String stockCode, String stockName,
                                        String currentPrice, String priceChange,
                                        String priceChangeRate, String marketCap,
                                        String per, String pbr,
                                        String high52Week, String low52Week) {
        return StockInfoResponse.builder()
                .stockCode(stockCode)
                .stockName(stockName)
                .currentPrice(currentPrice)
                .priceChange(priceChange)
                .priceChangeRate(priceChangeRate)
                .marketCap(marketCap)
                .per(per)
                .pbr(pbr)
                .high52Week(high52Week)
                .low52Week(low52Week)
                .build();
    }
}