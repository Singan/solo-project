package com.my.board.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
@Schema(description = "게시글 리스트 조회 결과")

public record ListResult(
        @Schema(description = "요청한 페이지 넘버") int pageNo,
        @Schema(description = "요청한 페이지 넘버 결과 리스트")List<BoardListViewDto> list,
        @Schema(description = "이전 버튼 존재 여부")boolean prev,
        @Schema(description = "다음 버튼 존재 여부")boolean next,
        @Schema(description = "남은 글의 개수")int leftSize
) {
}
