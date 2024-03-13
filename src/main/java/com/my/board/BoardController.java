package com.my.board;

import com.my.board.vo.BoardInsertDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping
    public Long boardInsert(@RequestBody BoardInsertDto boardInsertDto){
        return boardService.boardInsert(boardInsertDto);
    }

}
