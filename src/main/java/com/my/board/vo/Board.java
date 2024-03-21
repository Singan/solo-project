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
    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    private String content;
    @Builder
    public Board(Long id,String title, Long writer, String content) {
        this.id = id;
        this.title = title;
        this.writer = User.builder().no(writer).build();
        this.content = content;
    }
}
