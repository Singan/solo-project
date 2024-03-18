package com.my.user.vo;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private String id;
    private String pw;

    private String name;
    private String type;

    @Builder
    public User(Long no,String id, String pw, String name, String type) {
        this.no = no;
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.type = type;
    }

    public User(Long no) {
        this.no = no;
    }
}
