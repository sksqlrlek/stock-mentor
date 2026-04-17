package com.stockmentor.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.stockmentor.dto.WatchlistAddRequest;
import com.stockmentor.dto.WatchlistResponse;

public interface WatchlistService {
    
    // 관심 종목 조회
    @Transactional(readOnly = true)
    List<WatchlistResponse> getWatchlist(Long userId);

    // 관심 종목 추가
    @Transactional
    WatchlistResponse addToWatchlist(Long userId, WatchlistAddRequest request);

    // 관심 종목 삭제
    @Transactional
    void deleteFromWatchlist(Long userId, Long watchlistId);
}
