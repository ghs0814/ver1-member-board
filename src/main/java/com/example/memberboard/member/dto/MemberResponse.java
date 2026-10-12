package com.example.memberboard.member.dto;

import java.time.LocalDateTime;

import com.example.memberboard.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponse {

    private Long id;

    private String name;

    private String email;

    private String status;

    private LocalDateTime createdAt;

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getStatus().name(), //enum값.name() -> 문자열 반환 ex)"ACTIVE"
                member.getCreatedAt()
        );
    }
    //다른 객체를 전달받아 새 객체를 생성하는 메서드를 정적 팩토리 메서드
}