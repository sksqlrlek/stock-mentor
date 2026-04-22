package com.stockmentor.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class StockIndicatorResponse {

    private List<Double> ma5;
    private List<Double> ma20;
    private List<Double> ma60;
    private List<Double> rsi;
    private List<Double> macdLine;
    private List<Double> signalLine;
    private List<Double> bollingerUpper;
    private List<Double> bollingerMiddle;
    private List<Double> bollingerLower;

    public static StockIndicatorResponse of(List<Double> ma5, List<Double> ma20, List<Double> ma60,
                                             List<Double> rsi, List<Double> macdLine, List<Double> signalLine,
                                             List<Double> bollingerUpper, List<Double> bollingerMiddle,
                                             List<Double> bollingerLower) {
        return StockIndicatorResponse.builder()
                .ma5(ma5)
                .ma20(ma20)
                .ma60(ma60)
                .rsi(rsi)
                .macdLine(macdLine)
                .signalLine(signalLine)
                .bollingerUpper(bollingerUpper)
                .bollingerMiddle(bollingerMiddle)
                .bollingerLower(bollingerLower)
                .build();
    }
}