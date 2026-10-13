package com.example.memberboard.board.controller;

import java.net.URI;
import java.util.List;

import com.example.memberboard.board.dto.BoardCreateRequest;
import com.example.memberboard.board.dto.BoardResponse;
import com.example.memberboard.board.dto.BoardUpdateRequest;
import com.example.memberboard.board.service.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoard(
            @PathVariable("id") Long id
    ) {
        BoardResponse response = boardService.getBoard(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BoardResponse>> getBoards() {

        List<BoardResponse> responses = boardService.getBoards();

        return ResponseEntity.ok(responses);
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