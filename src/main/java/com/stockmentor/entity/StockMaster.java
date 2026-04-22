package com.stockmentor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "STOCK_MASTER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockMaster {

    @Id
    @Column(name = "STOCK_CODE", length = 20)
    private String stockCode;

    @Column(name = "STOCK_NAME", nullable = false, length = 100)
    private String stockName;

    @Column(name = "MARKET", length = 20)
    private String market;

    public static StockMaster of(String stockCode, String stockName, String market) {
        StockMaster stockMaster = new StockMaster();
        stockMaster.stockCode = stockCode;
        stockMaster.stockName = stockName;
        stockMaster.market = market;
        return stockMaster;
    }
}