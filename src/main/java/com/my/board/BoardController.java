package com.my.board;

import com.my.board.exception.BoardErrorCode;
import com.my.board.exception.BoardException;
import com.my.board.vo.*;
import com.my.config.exception.ErrorResponse;
import com.my.config.exception.GlobalException;
import com.my.user.vo.UserDetailsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.security.sasl.AuthenticationException;


@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Tag(name = "게시판", description = "게시판 기능 API")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    @Operation(
            summary = "게시글 작성",
            description = "새로운 게시글을 작성합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "게시글 작성에 필요한 정보",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = BoardInsertDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "게시글 작성 성공",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }

    )
    public ResponseEntity<String> boardInsert(
            @RequestBody BoardInsertDto boardInsertDto,
            @AuthenticationPrincipal UserDetailsDto userDetailsDto
    ) {
        return ResponseEntity.ok("boardNo:" + boardService.boardInsert(boardInsertDto, userDetailsDto));
    }

    @GetMapping
    @Operation(
            summary = "게시글 목록 조회",
            description = "페이지별로 게시글 목록을 10개씩 조회합니다. 게시글은 ID 순서로 정렬됩니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "게시글 목록 반환",
                            content = @Content(schema = @Schema(implementation = ListResult.class))
                    )
            },
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "원하는 페이지 번호",
                            required = true,
                            schema = @Schema(type = "integer", example = "0")
                    )
            }
    )
    public ResponseEntity<ListResult> boardList(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
            @Parameter(hidden = true) Pageable pageable
    ) {
        return ResponseEntity.ok(boardService.boardList(pageable));
    }

    @GetMapping("/{boardNo}")
    @Operation(
            summary = "게시글 상세 조회",
            description = "특정 게시글의 상세 정보를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "게시글 상세 정보 반환",
                            content = @Content(schema = @Schema(implementation = BoardViewDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음" ,
                            content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    public ResponseEntity<BoardViewDto> boardDetail(@PathVariable("boardNo") Long boardNo) {
        return ResponseEntity.ok(boardService.boardDetail(boardNo));
    }

    @DeleteMapping("/{boardNo}")
    @Operation(
            summary = "게시글 삭제",
            description = "특정 게시글을 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
            },
            parameters ={
                    @Parameter(
                            required = true,
                            name = "boardNo",
                            description = "삭제할 번호",
                            schema = @Schema(type = "integer", example = "0")
                    )
            } 
    )
    public ResponseEntity<Void> boardDelete(
            @PathVariable Long boardNo,
            @AuthenticationPrincipal UserDetailsDto userDetailsDto
    ) throws Exception {
        try {
            boardService.boardDelete(boardNo, userDetailsDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new BoardException(BoardErrorCode.BOARD_NOT_FOUND);
        }
    }

    @PutMapping("/{boardNo}")
    @Operation(
            summary = "게시글 수정",
            description = "특정 게시글을 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
                    @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "게시글 업데이트에 필요한 정보",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = BoardUpdateDto.class)
                    )
            )
    )
    public ResponseEntity<Void> boardUpdate(
            @RequestBody BoardUpdateDto boardUpdateDto,
            @AuthenticationPrincipal UserDetailsDto userDetailsDto,
            @PathVariable Long boardNo
    ) {
        try {
            boardService.boardUpdate(boardUpdateDto, userDetailsDto, boardNo);
            return ResponseEntity.ok().build();
        } catch (AuthenticationException e) {
            throw new BoardException(BoardErrorCode.BOARD_NOT_FOUND);
        }
    }
}

