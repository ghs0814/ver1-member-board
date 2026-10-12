package com.example.memberboard.member.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//jpa가 아니므로 entity 어노테이션을 사용하지 않음
@Getter
@Setter
@NoArgsConstructor
public class Member {

    private Long id;

    private String name;

    private String email;

    private MemberStatus status;

    private LocalDateTime createdAt; // oracle의 timestamp 자료형에 대응하는 localdatetime
}