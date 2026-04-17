package com.stockmentor.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "WATCHLIST")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Watchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "watchlist_seq")
    @SequenceGenerator(name = "watchlist_seq", sequenceName = "WATCHLIST_SEQ", allocationSize = 1)
    @Column(name = "WATCHLIST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "STOCK_CODE", nullable = false, length = 20)
    private String stockCode;
    
    @Column(name = "STOCK_NAME", nullable = false, length = 100)
    private String stockName;

    @Column(name = "ADDED_AT", nullable = false, updatable = false)
    private LocalDateTime addedAt;

    @PrePersist
    protected void onCreate() {
        this.addedAt = LocalDateTime.now();
    }

    public static Watchlist of(User user, String stockCode, String stockName) {
        Watchlist watchlist = new Watchlist();
        watchlist.user = user;
        watchlist.stockCode = stockCode;
        watchlist.stockName = stockName;
        return watchlist;
    }
}
