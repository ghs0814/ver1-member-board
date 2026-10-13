package com.example.memberboard.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardCreateRequest {

    @NotNull // notblank는 문자열 전용 검증
    private Long memberId; //일단 직접

    @NotBlank
    @Size(max = 200)
    private String title;

    @NotBlank
    private String content;
}