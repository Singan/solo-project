package com.my.board;

import com.my.aop.LogClass;
import com.my.board.vo.*;
import com.my.user.vo.UserDetailsDto;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@LogClass
@Timed("board")
public class BoardController {

    private final BoardService boardService;


    @PostMapping
    public ResponseEntity boardInsert(@RequestBody BoardInsertDto boardInsertDto, @AuthenticationPrincipal UserDetailsDto userDetailsDto) {
        return ResponseEntity.ok("boardNo:" + boardService.boardInsert(boardInsertDto, userDetailsDto));
    }

    @GetMapping
    @Async
    public CompletableFuture<ResponseEntity> boardList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {

        return CompletableFuture.completedFuture(ResponseEntity.ok(boardService.boardList(pageable)));
    }

    @GetMapping("/{boardNo}")
    public ResponseEntity boardDetail(@PathVariable("boardNo") Long boardNo) {
        return ResponseEntity.ok(boardService.boardDetail(boardNo));
    }

    @DeleteMapping("/{boardNo}")
    public ResponseEntity boardDelete(@PathVariable Long boardNo, @AuthenticationPrincipal UserDetailsDto userDetailsDto) throws Exception {
        try {
            boardService.boardDelete(boardNo, userDetailsDto);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{boardNo}")
    public ResponseEntity boardUpdate(@RequestBody BoardUpdateDto boardUpdateDto,
                                      @AuthenticationPrincipal UserDetailsDto userDetailsDto, @PathVariable Long boardNo) {
        try {
            boardService.boardUpdate(boardUpdateDto, userDetailsDto, boardNo);

            return ResponseEntity.ok().build();
        } catch (AuthenticationException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
