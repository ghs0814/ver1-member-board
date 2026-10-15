package com.example.memberboard.board.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import com.example.memberboard.board.dto.BoardDetailResponse;
import com.example.memberboard.board.service.BoardService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BoardController.class) // mockMvc 사용 선언
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc; // 주입

    @MockitoBean
    private BoardService boardService; // 서비스는 mockitobean

    @Test
    void getBoard_returnsBoardDetail_whenBoardExists() throws Exception {
        // given
        Long boardId = 1L;

        BoardDetailResponse response =
                new BoardDetailResponse();

        response.setId(boardId);
        response.setMemberId(10L);
        response.setMemberName("Kim");
        response.setTitle("Spring Boot Study");
        response.setContent("Test Content");
        response.setStatus("ACTIVE");
        response.setCreatedAt(
                LocalDateTime.of(2026, 10, 13, 10, 0)
        );
        response.setUpdatedAt(
                LocalDateTime.of(2026, 10, 13, 11, 0)
        );

        given(boardService.getBoardDetail(boardId))
                .willReturn(response);

        // when & then
        mockMvc.perform(
                        get("/api/boards/{id}", boardId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.memberId").value(10))
                .andExpect(jsonPath("$.memberName").value("Kim"))
                .andExpect(jsonPath("$.title")
                        .value("Spring Boot Study"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void getBoard_returnsNotFound_whenBoardDoesNotExist()
            throws Exception {

        // given
        Long boardId = 999L;

        given(boardService.getBoardDetail(boardId))
                .willThrow(
                        new NoSuchElementException(
                                "게시글을 찾을 수 없습니다. id="
                                        + boardId
                        )
                );

        // when & then
        mockMvc.perform(
                        get("/api/boards/{id}", boardId)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error")
                        .value("NOT_FOUND"))
                .andExpect(jsonPath("$.message")
                        .value(
                                "게시글을 찾을 수 없습니다. id="
                                        + boardId
                        ));
    }
}