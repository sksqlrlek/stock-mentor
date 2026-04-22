package com.stockmentor.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockmentor.client.StockDataProvider;
import com.stockmentor.client.StockSearchProvider;
import com.stockmentor.dto.ChartDataResponse;
import com.stockmentor.dto.StockIndicatorResponse;
import com.stockmentor.dto.StockInfoResponse;
import com.stockmentor.dto.StockSearchResponse;
import com.stockmentor.exception.BusinessException;
import com.stockmentor.exception.ErrorCode;
import com.stockmentor.util.StockIndicatorCalculator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockServiceImpl implements StockService{

    private final StockDataProvider stockDataProvider;
    private final StockSearchProvider stockSearchProvider;

    @Override
    public List<StockSearchResponse> searchStock(String keyword) {
    log.info("종목 검색 요청 - keyword: {}", keyword);
    return stockSearchProvider.search(keyword);
}

    @Override
    public StockInfoResponse getStockInfo(String stockCode) {
        log.info("종목 정보 조회 요청 - stockCode: {}", stockCode);
        Map<String, Object> response = stockDataProvider.fetchStockInfo(stockCode);

        Map<String, Object> output = (Map<String, Object>) response.get("output");
        if (output == null) {
            throw new BusinessException(ErrorCode.STOCK_NOT_FOUND);
        }

        return StockInfoResponse.of(
                stockCode,
                (String) output.get("hts_kor_isnm"),
                (String) output.get("stck_prpr"),
                (String) output.get("prdy_vrss"),
                (String) output.get("prdy_ctrt"),
                (String) output.get("hts_avls"),
                (String) output.get("per"),
                (String) output.get("pbr"),
                (String) output.get("w52_hgpr"),
                (String) output.get("w52_lwpr")
        );
    }

    @Override
    public List<ChartDataResponse> getChartData(String stockCode, String periodCode) {
        log.info("차트 데이터 조회 요청 - stockCode: {}, periodCode: {}", stockCode, periodCode);

        String endDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String startDate = switch (periodCode) {
            case "D" -> LocalDate.now().minusMonths(6).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            case "W" -> LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            case "M" -> LocalDate.now().minusYears(3).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            default -> throw new BusinessException(ErrorCode.INVALID_INPUT);
        };

        Map<String, Object> response = stockDataProvider.fetchChartData(stockCode, startDate, endDate, periodCode);
        List<Map<String, Object>> output = (List<Map<String, Object>>) response.get("output2");

        if (output == null || output.isEmpty()) {
            return List.of();
        }

        return output.stream()
                .map(item -> ChartDataResponse.of(
                        (String) item.get("stck_bsop_date"),
                        (String) item.get("stck_oprc"),
                        (String) item.get("stck_hgpr"),
                        (String) item.get("stck_lwpr"),
                        (String) item.get("stck_clpr"),
                        (String) item.get("acml_vol")
                ))
                .toList();
    }

    @Override
    public StockIndicatorResponse getIndicators(String stockCode) {
        log.info("기술적 지표 조회 요청 - stockCode: {}", stockCode);

        String endDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String startDate = LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        Map<String, Object> response = stockDataProvider.fetchChartData(stockCode, startDate, endDate, "D");
        List<Map<String, Object>> output = (List<Map<String, Object>>) response.get("output2");

        if (output == null || output.isEmpty()) {
            throw new BusinessException(ErrorCode.STOCK_NOT_FOUND);
        }

        List<Double> closePrices = output.stream()
                .map(item -> Double.parseDouble((String) item.get("stck_clpr")))
                .toList();

        return StockIndicatorCalculator.calculate(closePrices);
    }
    
}
