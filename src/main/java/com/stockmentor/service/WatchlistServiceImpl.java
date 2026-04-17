package com.stockmentor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.stockmentor.dto.WatchlistAddRequest;
import com.stockmentor.dto.WatchlistResponse;
import com.stockmentor.entity.User;
import com.stockmentor.entity.Watchlist;
import com.stockmentor.exception.BusinessException;
import com.stockmentor.exception.ErrorCode;
import com.stockmentor.repository.UserRepository;
import com.stockmentor.repository.WatchlistRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WatchlistServiceImpl implements WatchlistService{

    private final WatchlistRepository watchlistRepo;
    private final UserService userService;

    @Override
    public List<WatchlistResponse> getWatchlist(Long userId) {
        User user = userService.findById(userId);

        return watchlistRepo.findByUserOrderByAddedAtDesc(user).stream().map(WatchlistResponse::from).toList();
    }

    @Override
    @Transactional
    public WatchlistResponse addToWatchlist(Long userId, WatchlistAddRequest request) {
        User user = userService.findById(userId);

        if(watchlistRepo.existsByUserAndStockCode(user, request.getStockCode())) {
            throw new BusinessException(ErrorCode.WATCHLIST_ALREADY_EXISTS);
        }

        Watchlist watchlist = Watchlist.of(user, request.getStockCode(), request.getStockName());
        Watchlist saved = watchlistRepo.save(watchlist);
        return WatchlistResponse.from(saved);
    }

    @Override
    @Transactional
    public void deleteFromWatchlist(Long userId, Long watchlistId) {
        User user = userService.findById(userId);

        Watchlist watchlist = watchlistRepo.findByIdAndUser(watchlistId, user).orElseThrow(() -> new BusinessException(ErrorCode.WATCHLIST_NOT_FOUND));

        watchlistRepo.delete(watchlist);
    }

}
