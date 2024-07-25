package com.my.board.exception;

import com.my.config.exception.ErrorCode;

public enum BoardErrorCode implements ErrorCode {
    BOARD_NOT_FOUND(404, "해당 게시글을 찾지 못하였습니다.", "BOARD_NOT_FOUND"),
    BOARD_ACCESS_DENIED(403, "해당 게시글에 대한 권한이 없습니다.", "BOARD_ACCESS_DENIED");

    private final int status;
    private final String message;
    private final String code;

    BoardErrorCode(int status, String message, String code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getCode() {
        return code;
    }
}
