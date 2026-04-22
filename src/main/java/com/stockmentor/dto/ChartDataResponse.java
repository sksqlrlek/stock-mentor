package com.stockmentor.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ChartDataResponse {

    private String date;
    private String openPrice;
    private String highPrice;
    private String lowPrice;
    private String closePrice;
    private String volume;

    public static ChartDataResponse of(String date, String openPrice, String highPrice,
                                        String lowPrice, String closePrice, String volume) {
        return ChartDataResponse.builder()
                .date(date)
                .openPrice(openPrice)
                .highPrice(highPrice)
                .lowPrice(lowPrice)
                .closePrice(closePrice)
                .volume(volume)
                .build();
    }
}