package com.my.board.vo;

import com.my.user.vo.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @JoinColumn
    @ManyToOne
    private User writer;

    private String content;
    @Builder
    public Board(String title, Long writer, String content) {
        this.title = title;
        this.writer = new User(writer);
        this.content = content;
    }
}
