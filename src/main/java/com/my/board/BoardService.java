package com.my.board;

import com.my.aop.LogClass;
import com.my.board.vo.*;
import com.my.user.vo.User;
import com.my.user.vo.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@LogClass
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    @Transactional

    public Long boardInsert(BoardInsertDto boardDto , UserDetailsDto userDetailsDto){
        return boardRepository.save(boardDto.createBoard(userDetailsDto)).getId();
    }
    public ListResult boardList(Pageable pageable){
        Page<BoardListViewDto> boardList = boardRepository.findPageableList(pageable);
        return new ListResult(boardList.getNumber() , boardList.toList() );
    }
    public BoardViewDto boardFindOne(Long boardNo){
        Board board = boardRepository.findById(boardNo).orElseThrow(() -> new RuntimeException("없는 글입니다."));

        return new BoardViewDto(board.getId() , board.getTitle() , board.getContent() , board.getWriter().getName());
    }
    @Transactional
    public void boardDelete(Long no , UserDetailsDto userDetailsDto){

        boardRepository.deleteByIdAndWriter(no, User.builder().no(userDetailsDto.getNo()).build());
    }
}
