package com.example.memberboard.board.controller;

import java.net.URI;
import java.util.List;

import com.example.memberboard.board.dto.BoardCreateRequest;
import com.example.memberboard.board.dto.BoardDetailResponse;
import com.example.memberboard.board.dto.BoardResponse;
import com.example.memberboard.board.dto.BoardUpdateRequest;
import com.example.memberboard.board.service.BoardService;

import com.example.memberboard.common.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(
            @Valid @RequestBody BoardCreateRequest request
    ) {
        BoardResponse response = boardService.createBoard(request);

        URI location = URI.create("/api/boards/" + response.getId());

        return ResponseEntity.created(location).body(response);
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoard(
            @PathVariable("id") Long id
    ) {
        BoardResponse response = boardService.getBoard(id);

        return ResponseEntity.ok(response);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoard(
            @PathVariable("id") Long id
    ) {
        BoardResponse response = boardService.getBoard(id);

        return ResponseEntity.ok(response);
    }

    /*@GetMapping
    public ResponseEntity<List<BoardResponse>> getBoards() {

        List<BoardResponse> responses = boardService.getBoards();

        return ResponseEntity.ok(responses);
    }*/

    @GetMapping
    public ResponseEntity<PageResponse<BoardDetailResponse>> getBoards(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size //size는 실제 반환 개수가 아닌 요청 최대 개수
    ) {

        PageResponse<BoardDetailResponse> response =
                boardService.getBoards(keyword,page, size);

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(
            @PathVariable("id") Long id,
            @Valid @RequestBody BoardUpdateRequest request
    ) {
        BoardResponse response = boardService.updateBoard(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(
            @PathVariable("id") Long id
    ) {
        boardService.deleteBoard(id);

        return ResponseEntity.noContent().build();
    }
}