package com.my.board.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;


public record BoardListViewDto(
        @Schema(description = "글 번호")
        Long id ,
        @Schema(description = "글 제목")
        String title,
        @Schema(description = "작성 일시")

        LocalDateTime dateTime) {
}
