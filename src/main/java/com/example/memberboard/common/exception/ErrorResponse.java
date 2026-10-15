package com.example.memberboard.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private int status; // 1 상태 코드

    private String error; // 2 에러 종류

    private String message; // 3 에러 메시지
}