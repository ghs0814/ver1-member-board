package com.example.memberboard.board.mapper;

import java.util.List;

import com.example.memberboard.board.domain.Board;

import com.example.memberboard.board.dto.BoardDetailResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardMapper {

    int insert(Board board);

    Board findById(@Param("id") Long id);

    List<Board> findAll();

    int update(Board board);

    int deleteById(@Param("id") Long id);

    BoardDetailResponse findDetailById(@Param("id") Long id);

    List<BoardDetailResponse> findAllWithMember(
            @Param("keyword") String keyword,
            @Param("offset") int offset,
            @Param("size") int size

    );

    long countAll(
            @Param("keyword") String keyword
    );

}