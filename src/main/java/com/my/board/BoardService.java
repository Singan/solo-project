package com.my.board;

import com.my.board.vo.Board;
import com.my.board.vo.BoardInsertDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Long boardInsert(BoardInsertDto boardDto){

        return boardRepository.save(boardDto.createBoard()).getId();
    }

}
