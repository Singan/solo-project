package com.my.board;

import com.my.board.vo.BoardInsertDto;
import com.my.user.vo.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;
    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping
    public Long boardInsert(@RequestBody BoardInsertDto boardInsertDto , @AuthenticationPrincipal UserDetailsDto userDetailsDto){
        log.info("user = {}", userDetailsDto);
        return boardService.boardInsert(boardInsertDto , userDetailsDto);
    }
    @GetMapping
    public String boardList(){
        return "test";
    }

    @DeleteMapping
    public void boardDelete(@RequestParam Long boardNo){
    }

}
