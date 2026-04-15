package com.stockmentor.exception;

import org.springframework.http.ResponseEntity;

public abstract class BaseExceptionHandler {
    
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse response = buildErrorResponse(errorCode, e.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(response);
    }

    protected ResponseEntity<ErrorResponse> handleUnexpectedException() {
        ErrorResponse response = buildErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
        return ResponseEntity.status(500).body(response);
    }

    protected ErrorResponse buildErrorResponse(ErrorCode errorCode, String message) {
        return ErrorResponse.of(errorCode, message);
    }
}
