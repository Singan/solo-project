package com.my.reply.vo;

import com.my.board.vo.Board;
import com.my.user.vo.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
    @CreatedDate
    private LocalDateTime dateTime;

    private String content;

    @Builder
    public Reply(Long replyNo, Long boardNo, Long writer, String content, LocalDateTime dateTime) {
        this.id = replyNo;
        this.board = Board.builder().id(boardNo).build();
        this.writer = User.builder().no(writer).build();
        this.content = content;
        this.dateTime = dateTime;
    }

    public void setContent(String content) {
        this.content = content;
    }
}