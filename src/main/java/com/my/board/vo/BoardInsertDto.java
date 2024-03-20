package com.my.board.vo;

import com.my.user.vo.User;
import com.my.user.vo.UserDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

public record BoardInsertDto(String title,String content) {

    public Board createBoard(UserDetailsDto userDetailsDto) {
        Long no = userDetailsDto.getNo();
        Board board = Board.
                builder().
                content(this.content).
                title(this.title).
                writer(no).
                build();

        return board;
    }

}
