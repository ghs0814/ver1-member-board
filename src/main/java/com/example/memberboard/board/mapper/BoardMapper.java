package com.example.memberboard.board.mapper;

import java.util.List;

import com.example.memberboard.board.domain.Board;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardMapper {

    int insert(Board board);

    Board findById(@Param("id") Long id);

    List<Board> findAll();

    int update(Board board);

    int deleteById(@Param("id") Long id);
}