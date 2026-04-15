package com.stockmentor.exception;

public enum ErrorCode {
    INVALID_INPUT(400, "INVALID_INPUT", "잘못된 입력입니다."),
    INVALID_CREDENTIALS(401, "INVALID_CREDENTIALS", "이메일 혹은 비밀번호가 일치하지 않습니다."),
    UNAUTHORIZED(401, "UNAUTHORIZED", "로그인이 필요합니다."),
    FORBIDDEN(403, "FORBIDDEN", "권한이 없습니다."),
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "존재하지 않는 회원입니다."),
    DUPLICATE_EMAIL(409, "DUPLICATE_EMAIL", "이미 사용 중인 이메일입니다."),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() { return status; }
    public String getCode() { return code; }
    public String getMessage() { return message; }
}
