package com.my.user.exception;

import com.my.config.exception.ErrorCode;

public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "유저를 찾지 못하였습니다."),
    USER_UNAUTHORIZED(401, "USER_UNAUTHORIZED", "유저 인증이 비정상입니다."),
    USER_ACCESS_DENIED(403, "USER_ACCESS_DENIED", "권한이 없습니다."),
    USER_ALREADY_EXISTS(403, "USER_ALREADY_EXISTS", "중복된 사용자입니다.");

    private final int status;
    private final String code;
    private final String message;

    UserErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

