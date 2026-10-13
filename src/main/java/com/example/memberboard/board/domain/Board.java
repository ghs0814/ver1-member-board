package com.example.memberboard.board.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Board {

    private Long id;

    private Long memberId;

    private String title;

    private String content; //CLOB이어도 STRING으로 관리

    private BoardStatus status;

    private LocalDateTime createdAt; //TIMESTAMP -> LOCALDATETIME

    private LocalDateTime updatedAt;
}