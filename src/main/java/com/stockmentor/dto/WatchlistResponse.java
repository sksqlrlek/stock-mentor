package com.stockmentor.dto;

import java.time.LocalDateTime;

import com.stockmentor.entity.Watchlist;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class WatchlistResponse {
    private Long id;
    private String stockCode;
    private String stockName;
    private LocalDateTime addedAt;

    public static WatchlistResponse from(Watchlist watchlist) {
        return WatchlistResponse.builder()
                .id(watchlist.getId())
                .stockCode(watchlist.getStockCode())
                .stockName(watchlist.getStockName())
                .addedAt(watchlist.getAddedAt())
                .build();
    }
}
