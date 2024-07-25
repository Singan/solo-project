package com.my.user.exception;

import com.my.board.exception.BoardErrorCode;
import com.my.config.exception.GlobalException;

public class UserException extends GlobalException {
    public UserException(UserErrorCode errorCode) {
        super(errorCode);
    }
}