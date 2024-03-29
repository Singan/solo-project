package com.my.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

public record UserJoinDto(String id,String pw, String name, String type) {

    public User getUser(PasswordEncoder passwordEncoder){
        User user = User.builder()
                .id(id)
                .pw(passwordEncoder.encode(pw))
                .type(type)
                .name(name)
                .build();
        return user;
    }
}
