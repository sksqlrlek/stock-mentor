package com.stockmentor.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.stockmentor.dto.StockIndicatorResponse;

public class StockIndicatorCalculator {

    public static StockIndicatorResponse calculate(List<Double> closePrices) {
        return StockIndicatorResponse.of(
                calculateMA(closePrices, 5),
                calculateMA(closePrices, 20),
                calculateMA(closePrices, 60),
                calculateRSI(closePrices, 14),
                calculateMACD(closePrices),
                calculateSignalLine(calculateMACD(closePrices), 9),
                calculateBollingerUpper(closePrices, 20),
                calculateMA(closePrices, 20),
                calculateBollingerLower(closePrices, 20)
        );
    }

    // 이동평균선 - 최근(period) 종가의 단순 평균을 날짜별로 계산
    private static List<Double> calculateMA(List<Double> prices, int period) {
        List<Double> ma = new ArrayList<>();
        for (int i = 0; i < prices.size(); i++) {
            if (i < period - 1) {
                ma.add(null);
                continue;
            }
            double sum = 0;
            for (int j = i - period + 1; j <= i; j++) {
                sum += prices.get(j);
            }
            ma.add(Math.round(sum / period * 100.0) / 100.0);
        }
        return ma;
    }

    // RSI - 14일간(period) 상승/하락 평균의 비율로 현재 시장이 과매수인지 과매도인지 0~100 사이 값으로 반환
    private static List<Double> calculateRSI(List<Double> prices, int period) {
        List<Double> rsi = new ArrayList<>();
        for (int i = 0; i < period; i++) {
            rsi.add(null);
        }

        for (int i = period; i < prices.size(); i++) {
            double gainSum = 0;
            double lossSum = 0;

            for (int j = i - period + 1; j <= i; j++) {
                double change = prices.get(j) - prices.get(j - 1);
                if (change > 0) gainSum += change;
                else lossSum += Math.abs(change);
            }

            double avgGain = gainSum / period;
            double avgLoss = lossSum / period;

            if (avgLoss == 0) {
                rsi.add(100.0);
                continue;
            }

            double rs = avgGain / avgLoss;
            rsi.add(Math.round((100 - (100 / (1 + rs))) * 100.0) / 100.0);
        }
        return rsi;
    }

    // 지수이동평균 - 최근 데이터에 더 높은 가중치를 주는 지수이동평균을 계산
    private static List<Double> calculateEMA(List<Double> prices, int period) {
        List<Double> ema = new ArrayList<>();
        double multiplier = 2.0 / (period + 1);

        for (int i = 0; i < period - 1; i++) {
            ema.add(null);
        }

        double sum = 0;
        for (int i = 0; i < period; i++) {
            sum += prices.get(i);
        }
        ema.add(sum / period);

        for (int i = period; i < prices.size(); i++) {
            double value = (prices.get(i) - ema.get(ema.size() - 1)) * multiplier + ema.get(ema.size() - 1);
            ema.add(Math.round(value * 100.0) / 100.0);
        }
        return ema;
    }

    // MACD - 12일 EMA에서 26일 EMA를 빼서 단기와 장기 추세의 차이를 계산
    private static List<Double> calculateMACD(List<Double> prices) {
        List<Double> ema12 = calculateEMA(prices, 12);
        List<Double> ema26 = calculateEMA(prices, 26);
        List<Double> macd = new ArrayList<>();

        for (int i = 0; i < prices.size(); i++) {
            if (ema12.get(i) == null || ema26.get(i) == null) {
                macd.add(null);
                continue;
            }
            macd.add(Math.round((ema12.get(i) - ema26.get(i)) * 100.0) / 100.0);
        }
        return macd;
    }

    // 시그널 라인 - MACD의 9일 EMA로 MACD와 교차 시 매수/매도 신호를 판단하는 기준선
    private static List<Double> calculateSignalLine(List<Double> macd, int period) {
        List<Double> validMacd = macd.stream()
                .filter(v -> v != null)
                .collect(Collectors.toList());
        List<Double> signal = calculateEMA(validMacd, period);

        List<Double> result = new ArrayList<>();
        int nullCount = (int) macd.stream().filter(v -> v == null).count();
        for (int i = 0; i < nullCount; i++) {
            result.add(null);
        }
        result.addAll(signal);
        return result;
    }

    // 볼린저 밴드 - MA20에 표준편차 2배를 더해서 가격의 상단 저항선을 계산
    private static List<Double> calculateBollingerUpper(List<Double> prices, int period) {
        List<Double> upper = new ArrayList<>();
        for (int i = 0; i < prices.size(); i++) {
            if (i < period - 1) {
                upper.add(null);
                continue;
            }
            double ma = calculateMA(prices, period).get(i);
            double std = calculateStd(prices, i, period);
            upper.add(Math.round((ma + std * 2) * 100.0) / 100.0);
        }
        return upper;
    }

    // 볼린저 밴드 - MA20에 표준편차 2배를 더해서 가격의 하단 저항선을 계산
    private static List<Double> calculateBollingerLower(List<Double> prices, int period) {
        List<Double> lower = new ArrayList<>();
        for (int i = 0; i < prices.size(); i++) {
            if (i < period - 1) {
                lower.add(null);
                continue;
            }
            double ma = calculateMA(prices, period).get(i);
            double std = calculateStd(prices, i, period);
            lower.add(Math.round((ma - std * 2) * 100.0) / 100.0);
        }
        return lower;
    }

    // 종가의 표준편차
    private static double calculateStd(List<Double> prices, int index, int period) {
        double ma = 0;
        for (int i = index - period + 1; i <= index; i++) {
            ma += prices.get(i);
        }
        ma /= period;

        double variance = 0;
        for (int i = index - period + 1; i <= index; i++) {
            variance += Math.pow(prices.get(i) - ma, 2);
        }
        return Math.sqrt(variance / period);
    }
}
