package com.stockmentor.client;

import java.util.List;

import org.springframework.stereotype.Component;

import com.stockmentor.dto.StockSearchResponse;
import com.stockmentor.repository.StockMasterRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DbStockSearchProvider implements StockSearchProvider {

    private final StockMasterRepository stockMasterRepository;

    @Override
    public List<StockSearchResponse> search(String keyword) {
        return stockMasterRepository
                .findByStockNameContainingOrStockCodeContaining(keyword, keyword)
                .stream()
                .map(stock -> StockSearchResponse.of(
                        stock.getStockCode(),
                        stock.getStockName(),
                        stock.getMarket()
                ))
                .toList();
    }
}
