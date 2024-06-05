package com.backend.backend.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.backend.backend.dto.CommentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.backend.common.ApiResponseDto;
import com.backend.backend.common.ResponseUtils;
import com.backend.backend.dto.BoardRequestsDto;
import com.backend.backend.dto.BoardResponseDto;
import com.backend.backend.entity.Board;
import com.backend.backend.entity.Comment;
import com.backend.backend.entity.Meeting;
import com.backend.backend.entity.User;
import com.backend.backend.entity.enumSet.ErrorType;
import com.backend.backend.exception.RestApiException;
import com.backend.backend.repository.BoardRepository;
import com.backend.backend.repository.CommentRepository;
import com.backend.backend.repository.MeetingRepository;
import com.backend.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private CommentRepository commentRepository;

    // 게시글 전체 목록 조회
    @Transactional(readOnly = true)
    public ApiResponseDto<List<BoardResponseDto>> getPosts() {

        List<Board> boardList = boardRepository.findAllByOrderByModifiedAtDesc();
        List<BoardResponseDto> responseDtoList = new ArrayList<>();

        for (Board board : boardList) {
            //김민형 수정 제안
            //이부분 commentrepository에서 받아오는 걸로 수정하면 될 것 같아요!
            //아래처럼 수정했습니다. 근데 게시글에서 댓글 개수만 불러오면 되지 않나요??
            //게시글 들어가서만 댓글 보면 되지 않을까용? 그렇게 하면 대댓글 제외부분 작성 안해도 되니까요.. 
            List<Comment> commentList = commentRepository.findByBoard(board);
            commentList.sort(Comparator.comparing(Comment::getModifiedAt)
                            .reversed());
            List<CommentResponseDto> commentResponseDtoList = commentList.stream()
                    .map(CommentResponseDto::from)
                    .collect(Collectors.toList());
            
            // //기존코드
            // // 댓글리스트 작성일자 기준 내림차순 정렬
            // board.getCommentList()
            //         .sort(Comparator.comparing(Comment::getModifiedAt)
            //                 .reversed());

            // // 대댓글은 제외 부분 작성
            // List<CommentResponseDto> commentList = new ArrayList<>();
            // for (Comment comment : board.getCommentList()) {
            //     if (comment.getParentCommentId() == null) {
            //         commentList.add(CommentResponseDto.from(comment));
            //     }
            // }

            // List<BoardResponseDto> 로 만들기 위해 board 를 BoardResponseDto 로 만들고, list 에 dto 를 하나씩 넣는다.
            responseDtoList.add(BoardResponseDto.from(board, commentResponseDtoList));//대댓글 빠지면서 뭔가 오류가 납니다...!
        }

        return ResponseUtils.ok(responseDtoList);

    }

    // 게시글 작성
    @Transactional
    public ApiResponseDto<BoardResponseDto> createPost(BoardRequestsDto requestsDto, Long meetingId, String email) {

        User user = userRepository.findByEmail(email).orElse(null);//작성자
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);//해당미팅

        //작성자==주최자
        if(user.getId() != meeting.getUser().getId()){
            return null;
        }


        // 작성 글 저장
        Board board = boardRepository.save(Board.of(requestsDto, meeting));

        // BoardResponseDto 로 변환 후 responseEntity body 에 담아 반환
        return ResponseUtils.ok(BoardResponseDto.from(board));

    }

    // 선택된 게시글 조회
    @Transactional(readOnly = true)
    public ApiResponseDto<BoardResponseDto> getPost(Long id) {
        // Id에 해당하는 게시글이 있는지 확인
        Board board = boardRepository.findById(id).orElseThrow(() -> new RestApiException(ErrorType.NOT_FOUND_WRITING)); // 예외처리하도록 수정

        //김민형 수정
        List<Comment> commentList = commentRepository.findByBoard(board);
            commentList.sort(Comparator.comparing(Comment::getModifiedAt)
                            .reversed());
        List<CommentResponseDto> commentResponseDtoList = commentList.stream()
                .map(CommentResponseDto::from)
                .collect(Collectors.toList());
        
        //기존--근데 조회하는데 대댓글 왜 제외해야하죠...?
        // 댓글리스트 작성일자 기준 내림차순 정렬
        // board.get()
        //         .getCommentList()
        //         .sort(Comparator.comparing(Comment::getModifiedAt)
        //                 .reversed());

        // // 대댓글은 제외 부분 작성
        // List<CommentResponseDto> commentList = new ArrayList<>();
        // for (Comment comment : board.get().getCommentList()) {
        //     if (comment.getParentCommentId() == null) {
        //         commentList.add(CommentResponseDto.from(comment));
        //     }
        // }

        // board 를 responseDto 로 변환 후, ResponseEntity body 에 dto 담아 리턴
        return ResponseUtils.ok(BoardResponseDto.from(board, commentResponseDtoList));
    }

    // 선택된 게시글 수정
    @Transactional
    public ApiResponseDto<BoardResponseDto> updatePost(Long id, BoardRequestsDto requestsDto, String email) {

        // 선택한 게시글이 DB에 있는지 확인
        Board board = boardRepository.findById(id).orElse(null);
        if (board==null) {
            throw new RestApiException(ErrorType.NOT_FOUND_WRITING);
        }
        User user = userRepository.findByEmail(email).orElse(null);//작성자
        Meeting meeting = meetingRepository.findById(board.getMeeting().getId()).orElseThrow(() -> new RestApiException(ErrorType.NOT_FOUND_WRITING));//해당 미팅

        //작성자==주최자
        if(user.getId() != meeting.getUser().getId()){
            throw new RestApiException(ErrorType.NOT_WRITER);
        }



        // 모임 주최자 id 와 사용자 정보 일치한다면, 게시글 수정
        board.update(requestsDto, meeting);
        boardRepository.flush(); // responseDto 에 modifiedAt 업데이트 해주기 위해 flush 사용

        return ResponseUtils.ok(BoardResponseDto.from(board));

    }

}
