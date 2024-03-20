package com.my.board.vo;

import java.util.List;

public record ListResult(int pageNo , List<BoardListViewDto> list) {
}
