package com.stockmentor.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stockmentor.dto.NewsResponse;
import com.stockmentor.service.NewsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
    
    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<List<NewsResponse>> getNews(@RequestParam String keyword) {
        return ResponseEntity.ok(newsService.getNews(keyword));
    }
}
