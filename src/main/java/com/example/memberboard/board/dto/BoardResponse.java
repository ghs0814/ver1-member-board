package com.example.memberboard.board.dto;

import java.time.LocalDateTime;

import com.example.memberboard.board.domain.Board;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardResponse {

    private Long id;

    private Long memberId;

    private String title;

    private String content;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static BoardResponse from(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getMemberId(),
                board.getTitle(),
                board.getContent(),
                board.getStatus().name(),
                board.getCreatedAt(),
                board.getUpdatedAt()
        );
    }
}