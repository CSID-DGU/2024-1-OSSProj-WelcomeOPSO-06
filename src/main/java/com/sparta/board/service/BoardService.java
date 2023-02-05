package com.sparta.board.service;

import com.sparta.board.dto.BoardRequestsDto;
import com.sparta.board.dto.BoardResponseDto;
import com.sparta.board.dto.SuccessResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getPosts() {
        List<Board> posts = boardRepository.findAllByOrderByModifiedAtDesc();
        List<BoardResponseDto> responseDtos = new ArrayList<>();
        for (Board post : posts) {
            responseDtos.add(new BoardResponseDto(post.getId(), post.getTitle(), post.getContents(), post.getAuthor(), post.getCreatedAt(), post.getModifiedAt()));
        }
        return responseDtos;
    }

    @Transactional
    public BoardResponseDto createPost(BoardRequestsDto requestsDto) {
        Board board = new Board(requestsDto);
        boardRepository.save(board);
        return new BoardResponseDto(board.getId(), board.getTitle(), board.getContents(), board.getAuthor(), board.getCreatedAt(), board.getModifiedAt());
    }

    @Transactional
    public BoardResponseDto getPost(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new BoardResponseDto(board.getId(), board.getTitle(), board.getContents(), board.getAuthor(), board.getCreatedAt(), board.getModifiedAt());
    }

    @Transactional
    public BoardResponseDto updatePost(Long id, BoardRequestsDto requestsDto) throws Exception {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (!requestsDto.getPassword().equals(board.getPassword()))
            throw new Exception("비밀번호가 일치하지 않습니다.");

        board.update(requestsDto);
        return new BoardResponseDto(board.getId(), board.getTitle(), board.getContents(), board.getAuthor(), board.getCreatedAt(), board.getModifiedAt());
    }

    @Transactional
    public SuccessResponseDto deletePost(Long id, BoardRequestsDto requestsDto) throws Exception {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        if (!requestsDto.getPassword().equals(board.getPassword()))
            throw new Exception("비밀번호가 일치하지 않습니다.");

        boardRepository.deleteById(id);
        return new SuccessResponseDto(true);
    }
}
