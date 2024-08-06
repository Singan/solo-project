package com.my.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
@Builder
public record UserJoinDto(String id,String pw, String name , String email, String phone) {

    public User getUser(PasswordEncoder passwordEncoder){
        User user = User.builder()
                .id(id)
                .pw(passwordEncoder.encode(pw))
                .name(name)
                .email(email)
                .phone(phone)
                .build();
        return user;
    }
}
