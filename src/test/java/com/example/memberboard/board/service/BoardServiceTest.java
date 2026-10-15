package com.example.memberboard.board.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import com.example.memberboard.board.domain.Board;
import com.example.memberboard.board.domain.BoardStatus;
import com.example.memberboard.board.dto.BoardCreateRequest;
import com.example.memberboard.board.dto.BoardDetailResponse;
import com.example.memberboard.board.dto.BoardResponse;
import com.example.memberboard.board.mapper.BoardMapper;
import com.example.memberboard.member.domain.Member;
import com.example.memberboard.member.domain.MemberStatus;
import com.example.memberboard.member.mapper.MemberMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(BoardService.class)
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @MockitoBean
    private BoardMapper boardMapper;

    @MockitoBean
    private MemberMapper memberMapper;

    @Test
    void getBoardDetail_returnsBoardDetail_whenBoardExists() {
        // given
        Long boardId = 1L;

        BoardDetailResponse expected = new BoardDetailResponse();
        expected.setId(boardId);
        expected.setMemberId(10L);
        expected.setMemberName("Kim");
        expected.setTitle("Spring Boot Study");
        expected.setContent("Test Content");
        expected.setStatus("ACTIVE");
        expected.setCreatedAt(LocalDateTime.now());
        expected.setUpdatedAt(LocalDateTime.now());

        given(boardMapper.findDetailById(boardId))
                .willReturn(expected);

        // when
        BoardDetailResponse actual =
                boardService.getBoardDetail(boardId);

        // then
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getMemberId(), actual.getMemberId());
        assertEquals(expected.getMemberName(), actual.getMemberName());
        assertEquals(expected.getTitle(), actual.getTitle());

        verify(boardMapper).findDetailById(boardId);
    }

    @Test
    void getBoardDetail_throwsException_whenBoardDoesNotExist() {
        // given
        Long boardId = 999L;

        given(boardMapper.findDetailById(boardId))
                .willReturn(null);

        // when
        NoSuchElementException exception =
                assertThrows(
                        NoSuchElementException.class,
                        () -> boardService.getBoardDetail(boardId)
                );

        // then
        assertEquals(
                "게시글을 찾을 수 없습니다. id=" + boardId,
                exception.getMessage()
        );

        verify(boardMapper).findDetailById(boardId);
    }

    @Test
    void createBoard_returnsCreatedBoard_whenMemberExists() {
        // given
        Long memberId = 10L;
        Long boardId = 100L;

        BoardCreateRequest request = new BoardCreateRequest();
        request.setMemberId(memberId);
        request.setTitle("Spring Boot Test");
        request.setContent("Board Test Content");

        Member member = new Member();
        member.setId(memberId);
        member.setName("Kim");
        member.setEmail("kim@example.com");
        member.setStatus(MemberStatus.ACTIVE);
        member.setCreatedAt(LocalDateTime.now());

        Board savedBoard = new Board();
        savedBoard.setId(boardId);
        savedBoard.setMemberId(memberId);
        savedBoard.setTitle(request.getTitle());
        savedBoard.setContent(request.getContent());
        savedBoard.setStatus(BoardStatus.ACTIVE);
        savedBoard.setCreatedAt(LocalDateTime.now());
        savedBoard.setUpdatedAt(LocalDateTime.now());

        given(memberMapper.findById(memberId))
                .willReturn(member);

        given(boardMapper.insert(any(Board.class)))
                .willAnswer(invocation -> {
                    Board board = invocation.getArgument(0);
                    board.setId(boardId);
                    return 1;
                });

        given(boardMapper.findById(boardId))
                .willReturn(savedBoard);

        // when
        BoardResponse actual =
                boardService.createBoard(request);

        // then
        assertEquals(boardId, actual.getId());
        assertEquals(memberId, actual.getMemberId());
        assertEquals(request.getTitle(), actual.getTitle());
        assertEquals(request.getContent(), actual.getContent());
        assertEquals("ACTIVE", actual.getStatus());

        verify(memberMapper).findById(memberId);
        verify(boardMapper).insert(any(Board.class));
        verify(boardMapper).findById(boardId);
    }

    @Test
    void createBoard_throwsException_whenMemberDoesNotExist() {
        // given
        Long memberId = 999L;

        BoardCreateRequest request = new BoardCreateRequest();
        request.setMemberId(memberId);
        request.setTitle("Test Title");
        request.setContent("Test Content");

        given(memberMapper.findById(memberId))
                .willReturn(null);

        // when
        NoSuchElementException exception =
                assertThrows(
                        NoSuchElementException.class,
                        () -> boardService.createBoard(request)
                );

        // then
        assertEquals(
                "작성자를 찾을 수 없습니다. memberId=" + memberId,
                exception.getMessage()
        );

        verify(memberMapper).findById(memberId);
        verify(boardMapper, never())
                .insert(any(Board.class));
    }
}