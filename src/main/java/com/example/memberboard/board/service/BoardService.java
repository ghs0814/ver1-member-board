package com.example.memberboard.board.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.example.memberboard.board.domain.Board;
import com.example.memberboard.board.dto.BoardCreateRequest;
import com.example.memberboard.board.dto.BoardDetailResponse;
import com.example.memberboard.board.dto.BoardResponse;
import com.example.memberboard.board.dto.BoardUpdateRequest;
import com.example.memberboard.board.mapper.BoardMapper;
import com.example.memberboard.common.dto.PageResponse;
import com.example.memberboard.member.domain.Member;
import com.example.memberboard.member.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;
    private final MemberMapper memberMapper;

    @Transactional
    public BoardResponse createBoard(BoardCreateRequest request) {

        Member member = memberMapper.findById(request.getMemberId());

        if (member == null) {
            throw new NoSuchElementException(
                    "작성자를 찾을 수 없습니다. memberId=" + request.getMemberId()
            );
        }

        Board board = new Board();
        board.setMemberId(request.getMemberId());
        board.setTitle(request.getTitle());
        board.setContent(request.getContent());

        int insertedRows = boardMapper.insert(board);

        if (insertedRows != 1) {
            throw new IllegalStateException("게시글 등록에 실패했습니다.");
        }

        Board savedBoard = boardMapper.findById(board.getId());

        if (savedBoard == null) {
            throw new IllegalStateException("등록된 게시글을 조회할 수 없습니다.");
        }

        return BoardResponse.from(savedBoard);
    }

    @Transactional(readOnly = true)
    public BoardResponse getBoard(Long id) {

        Board board = boardMapper.findById(id);

        if (board == null) {
            throw new NoSuchElementException(
                    "게시글을 찾을 수 없습니다. id=" + id
            );
        }

        return BoardResponse.from(board);
    }

   /* @Transactional(readOnly = true)
    public List<BoardResponse> getBoards() {

        return boardMapper.findAll()
                .stream()
                .map(BoardResponse::from)
                .toList();
    }*/

    @Transactional(readOnly = true)
    public PageResponse<BoardDetailResponse> getBoards(
            String keyword,
            int page,
            int size
    ) {

        if (page < 0) {
            throw new IllegalArgumentException(
                    "page는 0 이상이어야 합니다."
            );
        }

        if (size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "size는 1 이상 100 이하여야 합니다."
            );
        }

        String normalizedKeyword =
                keyword == null ? "" : keyword.trim();

        int offset = page * size;

        List<BoardDetailResponse> content =
                boardMapper.findAllWithMember(
                        normalizedKeyword,
                        offset,
                        size
                );

        long totalElements =
                boardMapper.countAll(normalizedKeyword);

        int totalPages =
                (int) ((totalElements + size - 1) / size);

        return new PageResponse<>(
                content,
                page,
                size,
                totalElements,
                totalPages
        );
    }


    @Transactional
    public BoardResponse updateBoard(
            Long id,
            BoardUpdateRequest request
    ) {

        Board board = boardMapper.findById(id);

        if (board == null) {
            throw new NoSuchElementException(
                    "게시글을 찾을 수 없습니다. id=" + id
            );
        }

        board.setTitle(request.getTitle());
        board.setContent(request.getContent());

        int updatedRows = boardMapper.update(board);

        if (updatedRows != 1) {
            throw new IllegalStateException("게시글 수정에 실패했습니다.");
        }

        Board updatedBoard = boardMapper.findById(id);

        if (updatedBoard == null) {
            throw new IllegalStateException("수정된 게시글을 조회할 수 없습니다.");
        }

        return BoardResponse.from(updatedBoard);
    }

    @Transactional
    public void deleteBoard(Long id) {

        Board board = boardMapper.findById(id);

        if (board == null) {
            throw new NoSuchElementException(
                    "게시글을 찾을 수 없습니다. id=" + id
            );
        }

        int updatedRows = boardMapper.deleteById(id);

        if (updatedRows != 1) {
            throw new IllegalStateException("게시글 삭제에 실패했습니다.");
        }
    }

    @Transactional(readOnly = true)
    public BoardDetailResponse getBoardDetail(Long id) {

        BoardDetailResponse response = boardMapper.findDetailById(id);

        if (response == null) {
            throw new NoSuchElementException(
                    "게시글을 찾을 수 없습니다. id=" + id
            );
        }

        return response;
    }


}