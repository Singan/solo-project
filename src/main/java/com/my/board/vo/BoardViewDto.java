package com.my.board.vo;

import com.my.reply.vo.ReplyListDto;

public record BoardViewDto(Long no , String title, String content , String writer , ReplyListDto replyList , long views) {
}
