package com.stockmentor.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    //User 관련
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "INVALID_INPUT", "잘못된 입력입니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS", "이메일 혹은 비밀번호가 일치하지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "로그인이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", "권한이 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "존재하지 않는 회원입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "DUPLICATE_EMAIL", "이미 사용 중인 이메일입니다."),
    
    // Watchlist 관련
    WATCHLIST_ALREADY_EXISTS(HttpStatus.CONFLICT, "WATCHLIST_ALREADY_EXISTS", "이미 관심 종목에 등록된 종목입니다."),
    WATCHLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "WATCHLIST_NOT_FOUND", "해당 관심 종목을 찾을 수 없습니다."),

    // KIS API 관련
    KIS_TOKEN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "KIS_TOKEN_ERROR", "KIS API 토큰 발급에 실패했습니다."),
    KIS_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "KIS_API_ERROR", "KIS API 호출에 실패했습니다."),
    
    // 네이버 뉴스 관련
    NEWS_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "NEWS_API_ERROR", "뉴스 데이터 조회에 실패했습니다."),

    // Stock 관련
    STOCK_NOT_FOUND(HttpStatus.NOT_FOUND, "STOCK_NOT_FOUND", "존재하지 않는 종목입니다."),

    // 기타
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() { return status; }
    public String getCode() { return code; }
    public String getMessage() { return message; }
}
