package com.my.user.vo;

import lombok.Data;

@Data
public class UserJoinDto {

    private String id;
    private String pw;

    private String name;
    private String type;

    public User getUser(){
        User user = User.builder()
                .id(id)
                .pw(pw)
                .type(type)
                .name(name)
                .build();
        return user;
    }
}
