package com.stockmentor.client;

import java.util.Map;

public interface StockDataProvider {

    // 종목 현재가 조회
    Map<String, Object> fetchStockInfo(String stockCode);

    // 차트 데이터 조회
    Map<String, Object> fetchChartData(String stockCode, String startDate, String endDate, String periodCode);

}