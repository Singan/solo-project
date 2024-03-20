package com.my.board;

import com.my.aop.LogClass;
import com.my.board.vo.Board;
import com.my.board.vo.BoardInsertDto;
import com.my.board.vo.BoardListViewDto;
import com.my.board.vo.ListResult;
import com.my.user.vo.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@LogClass

public class BoardService {

    private final BoardRepository boardRepository;
    public Long boardInsert(BoardInsertDto boardDto , UserDetailsDto userDetailsDto){
        return boardRepository.save(boardDto.createBoard(userDetailsDto)).getId();
    }
    public ListResult boardList(Pageable pageable){
        Page<BoardListViewDto> boardList = boardRepository.findPageableList(pageable);
        return new ListResult(boardList.getNumber() , boardList.toList() );
    }
}
