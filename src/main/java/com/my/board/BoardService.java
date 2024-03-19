package com.my.board;

import com.my.aop.LogClass;
import com.my.aop.MyTest;
import com.my.board.vo.Board;
import com.my.board.vo.BoardInsertDto;
import com.my.user.vo.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@LogClass

public class BoardService {

    private final BoardRepository boardRepository;
    @MyTest
    public Long boardInsert(BoardInsertDto boardDto , UserDetailsDto userDetailsDto){
        return boardRepository.save(boardDto.createBoard(userDetailsDto)).getId();
    }

}
