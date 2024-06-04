package com.backend.backend.exception;

import com.backend.backend.entity.enumSet.ErrorType;
import lombok.Getter;

@Getter
public class RestApiException extends RuntimeException {
    private final ErrorType errorType;

    public RestApiException(ErrorType errorType) {
        this.errorType = errorType;
    }
}
