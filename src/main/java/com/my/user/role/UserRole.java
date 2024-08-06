package com.my.user.role;

import com.my.user.vo.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JoinColumn
    private Long userid;

    @Enumerated(EnumType.STRING)
    @Column
    private RoleEnum role;

    @Builder
    public UserRole(Long id, Long userid, RoleEnum role) {
        this.id = id;
        this.userid = userid;
        this.role = role;
    }
}
