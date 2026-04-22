package com.stockmentor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockmentor.entity.StockMaster;

public interface StockMasterRepository extends JpaRepository<StockMaster, String>{
    List<StockMaster> findByStockNameContainingOrStockCodeContaining(
            String stockName, String stockCode);
}
