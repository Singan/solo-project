package com.my.config.exception;

public interface ErrorCode {
    int getStatus();
    String getMessage();
    String getCode();
}