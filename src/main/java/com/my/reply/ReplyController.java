package com.my.reply;
import com.my.reply.vo.ReplyInsertDto;
import com.my.reply.vo.ReplyUpdateDto;
import com.my.user.vo.UserDetailsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
@Tag(name = "Reply", description = "댓글 관련 API")
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping
    @Operation(
            summary = "댓글 작성",
            description = "새로운 댓글을 작성합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "댓글 작성 성공",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    public void replyInsert(
            @AuthenticationPrincipal UserDetailsDto userDetailsDto,
            @RequestBody ReplyInsertDto replyInsertDto
    ) {
        replyService.replyInsert(replyInsertDto, userDetailsDto);
    }

    @PutMapping
    @Operation(
            summary = "댓글 수정",
            description = "기존 댓글을 수정합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "댓글 수정 성공",
                            content = @Content(schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 실패",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    public void replyUpdate(
            @AuthenticationPrincipal UserDetailsDto userDetailsDto,
            @RequestBody ReplyUpdateDto replyUpdateDto
    ) {
        replyService.replyUpdate(replyUpdateDto, userDetailsDto);
    }
}
