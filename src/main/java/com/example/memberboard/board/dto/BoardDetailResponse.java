package com.example.memberboard.board.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDetailResponse {

    private Long id;

    private Long memberId;

    private String memberName; // member table name

    private String title;

    private String content;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    //from 메서드가 없는 이유 : join 결과를 바로 이 dto 형태로 매핑하도록 연결할거기떄문에, domain -> dto 변환 없음
}