package com.my.config.exception;

public class GlobalException extends RuntimeException {
    private final ErrorCode errorCode;

    // 생성자: ErrorCode를 인자로 받아서 예외를 생성합니다.
    public GlobalException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // 부모 클래스의 생성자에 에러 메시지를 전달
        this.errorCode = errorCode; // ErrorCode를 저장
    }

    // 상태 코드를 반환하는 메서드
    public int getStatusCode() {
        return errorCode.getStatus();
    }

    // 에러 메시지를 반환하는 메서드
    public String getErrorMessage() {
        return errorCode.getMessage();
    }
    // 에러 메시지를 반환하는 메서드
    public String getCode() {
        return errorCode.getCode();
    }

    // ErrorCode 객체를 반환하는 메서드
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
