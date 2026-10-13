package com.example.memberboard.member.mapper;

import com.example.memberboard.member.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {

    int insert(Member member); //insert는 행 개수를 반환

    Member findById(@Param("id") Long id); //select는 객체를 반환

    List<Member> findAll();

    int update(Member member);

    int withdraw(@Param("id") Long id);
}