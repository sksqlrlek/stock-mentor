package com.stockmentor.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.stockmentor.dto.ChartDataResponse;
import com.stockmentor.dto.StockIndicatorResponse;
import com.stockmentor.dto.StockInfoResponse;
import com.stockmentor.dto.StockSearchResponse;

public interface StockService {
    // 종목 검색
    @Transactional(readOnly = true)
    List<StockSearchResponse> searchStock(String keyword);

    // 종목 현재가 조회
    @Transactional(readOnly = true)
    StockInfoResponse getStockInfo(String stockCode);

    // 차트 데이터 조회
    @Transactional(readOnly = true)
    List<ChartDataResponse> getChartData(String stockCode, String periodCode);

    // 기술적 지표 계산
    @Transactional(readOnly = true)
    StockIndicatorResponse getIndicators(String stockCode);
}
