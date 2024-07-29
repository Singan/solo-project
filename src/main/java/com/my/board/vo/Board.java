package com.my.board.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.reply.vo.Reply;
import com.my.user.vo.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @JsonIgnore
    private User writer;

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    @BatchSize(size = 2)
    @JsonIgnore
    private List<Reply> replyList = new ArrayList<>();

    @CreatedDate
    private LocalDateTime dateTime;

    private String content;
    @Builder
    public Board(Long id,String title, Long writer, String content , LocalDateTime dateTime) {
        this.id = id;
        this.title = title;
        this.writer = User.builder().no(writer).build();
        this.content = content;
        this.dateTime = dateTime;
    }

    public Long boardUpdate(String title , String content){
        this.title = title;
        this.content = content;
        dateTime = LocalDateTime.now();
        return this.getId();
    }

    public void addReply(Reply reply) {
        this.replyList.add(reply);
        reply.setBoard(this);
    }
}
