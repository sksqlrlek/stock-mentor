package com.stockmentor.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stockmentor.dto.ChartDataResponse;
import com.stockmentor.dto.StockIndicatorResponse;
import com.stockmentor.dto.StockInfoResponse;
import com.stockmentor.dto.StockSearchResponse;
import com.stockmentor.service.StockService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/stocks")
@RequiredArgsConstructor
@Slf4j
public class StockController {
    
    private final StockService stockService;

    // 종목 검색
    @GetMapping("/search")
    public ResponseEntity<List<StockSearchResponse>> searchStock(
        @RequestParam String q
    ) {
        return ResponseEntity.ok(stockService.searchStock(q));
    }

    // 종목 기본 정보 조회
    @GetMapping("/{stockCode}")
    public ResponseEntity<StockInfoResponse> getStockInfo(
        @PathVariable String stockCode
    ) {
        return ResponseEntity.ok(stockService.getStockInfo(stockCode));
    }

    // 종목 차트 조회
    @GetMapping("/{stockCode}/chart")
    public ResponseEntity<List<ChartDataResponse>> getChartData(
        @PathVariable String stockCode,
        @RequestParam(defaultValue = "D") String periodCode
    ) {
        return ResponseEntity.ok(stockService.getChartData(stockCode, periodCode));
    }

    // 기술적 지표 조회
    @GetMapping("/{stockCode}/indicators")
    public ResponseEntity<StockIndicatorResponse> getIndicators(
        @PathVariable String stockCode
    ) {
        return ResponseEntity.ok(stockService.getIndicators(stockCode));
    }
}
