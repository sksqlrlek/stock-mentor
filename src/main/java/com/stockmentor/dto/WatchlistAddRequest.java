package com.stockmentor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WatchlistAddRequest {

    @NotBlank(message = "종목 코드는 필수입니다.")
    @Size(max = 20, message = "종목 코드는 20자 이하여야 합니다.")
    private String stockCode;

    @NotBlank(message = "종목명은 필수입니다.")
    @Size(max = 100, message = "종목명은 100자 이하여야 합니다.")
    private String stockName;
}
