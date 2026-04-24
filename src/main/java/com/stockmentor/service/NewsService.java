package com.stockmentor.service;

import java.util.List;

import com.stockmentor.dto.NewsResponse;

public interface NewsService {
    List<NewsResponse> getNews(String keyword);
}
