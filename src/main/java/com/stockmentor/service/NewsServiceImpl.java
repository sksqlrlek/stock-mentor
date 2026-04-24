package com.stockmentor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stockmentor.client.NaverNewsClient;
import com.stockmentor.dto.NewsResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService{
    
    private final NaverNewsClient naverNewsClient;

    @Override
    public List<NewsResponse> getNews(String keyword) {
        return naverNewsClient.fetchNews(keyword);
    }

    

}
