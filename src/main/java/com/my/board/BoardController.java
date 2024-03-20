package com.my.board;

import com.my.aop.LogClass;
import com.my.board.vo.BoardInsertDto;
import com.my.board.vo.BoardListViewDto;
import com.my.board.vo.ListResult;
import com.my.user.vo.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@LogClass
public class BoardController {

    private final BoardService boardService;


    @PostMapping
    public Long boardInsert(@RequestBody BoardInsertDto boardInsertDto, @AuthenticationPrincipal UserDetailsDto userDetailsDto) {
        return boardService.boardInsert(boardInsertDto, userDetailsDto);
    }

    @GetMapping("/list")
    public ListResult boardList(
            @PageableDefault(size = 12, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return boardService.boardList(pageable);
    }

    @DeleteMapping
    public void boardDelete(@RequestParam Long boardNo) {
    }

}
