package com.stockmentor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockmentor.entity.User;
import com.stockmentor.entity.Watchlist;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

    // 관심 종목 조회(최신순)
    List<Watchlist> findByUserOrderByAddedAtDesc(User user);

    // 중복 체크
    boolean existsByUserAndStockCode(User user, String stockCode);

    // 본인 검증
    Optional<Watchlist> findByIdAndUser(Long id, User user);
} 
