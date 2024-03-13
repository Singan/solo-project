package com.my.board.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardInsertDto {

    private String title;
    private String content;

    public Board createBoard() {
        Board board = Board.
                builder().
                content(this.content).
                title(this.title).
                build();

        return board;
    }

}
