package com.stockmentor.client;

import java.util.List;

import com.stockmentor.dto.StockSearchResponse;

public interface StockSearchProvider {
    List<StockSearchResponse> search(String keyword);
}
