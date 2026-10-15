package com.example.memberboard.common.exception;

import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 1. 전역예외처리장소지정
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class) // 2. 예외 종류 지정
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(
            NoSuchElementException e
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND; // 3. 에러 상태 지정

        ErrorResponse response = new ErrorResponse(
                status.value(), // 4. 에러 코드
                status.name(), // 5. 에러 종류
                e.getMessage() // 6. 에러 메시지
        );

        return ResponseEntity //7. 에러 응답 객체 생
                .status(status)
                .body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException e
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse response = new ErrorResponse(
                status.value(),
                status.name(),
                e.getMessage()
        );

        return ResponseEntity
                .status(status)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // 상태 설정

        FieldError fieldError = e.getBindingResult()
                .getFieldError();

        String message;

        if (fieldError == null) {
            message = "요청값 검증에 실패했습니다.";
        } else {
            message = fieldError.getField()
                    + ": "
                    + fieldError.getDefaultMessage();
        }

        ErrorResponse response = new ErrorResponse(
                status.value(), //에러 코드
                "VALIDATION_FAILED", // 에러 종류
                message
        );

        return ResponseEntity
                .status(status)
                .body(response);
    }
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(
            ConflictException e
    ) {
        HttpStatus status = HttpStatus.CONFLICT;

        ErrorResponse response = new ErrorResponse(
                status.value(),
                status.name(),
                e.getMessage()
        );

        return ResponseEntity
                .status(status)
                .body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException e
    ) {
        HttpStatus status = HttpStatus.CONFLICT;

        ErrorResponse response = new ErrorResponse(
                status.value(),
                status.name(),
                "데이터 무결성 제약조건을 위반했습니다."
        );

        return ResponseEntity
                .status(status)
                .body(response);
    }

    @ExceptionHandler(Exception.class) // 예상하지 못한 예외까지 처리하기 위한 메서드
    public ResponseEntity<ErrorResponse> handleException(
            Exception e
    ) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponse response = new ErrorResponse(
                status.value(),
                status.name(),
                "서버 내부 오류가 발생했습니다."
        );

        return ResponseEntity
                .status(status)
                .body(response);
    }
}