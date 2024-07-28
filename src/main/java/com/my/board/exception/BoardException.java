package com.my.board.exception;

import com.my.config.exception.GlobalException;

public class BoardException extends GlobalException {
    public BoardException(BoardErrorCode errorCode) {
        super(errorCode);
    }
}
