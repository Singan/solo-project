package com.my.board.vo;

import lombok.Data;

import java.time.LocalDateTime;


public record BoardListViewDto(Long id , String title, LocalDateTime dateTime) {
}
