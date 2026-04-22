package com.stockmentor.client;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.stockmentor.client.kis.KisApiClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KisStockDataProvider implements StockDataProvider{

    private final KisApiClient kisApiClient;

    @Override
    public Map<String, Object> fetchStockInfo(String stockCode) {
        return kisApiClient.fetchStockInfo(stockCode);
    }

    @Override
    public Map<String, Object> fetchChartData(String stockCode, String startDate, String endDate, String periodCode) {
        return kisApiClient.fetchChartData(stockCode, startDate, endDate, periodCode);
    }
    
}
