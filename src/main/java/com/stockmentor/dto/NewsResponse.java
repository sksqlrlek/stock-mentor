package com.stockmentor.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class NewsResponse {

    private String title;
    private String description;
    private String link;
    private String pubDate;
    
    public static NewsResponse of(String title, String description, String link, String pubDate) {
        return NewsResponse.builder()
                .title(title)
                .description(description)
                .link(link)
                .pubDate(pubDate)
                .build();
    }
}
