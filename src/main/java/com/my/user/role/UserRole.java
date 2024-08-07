package com.my.user.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.my.user.vo.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity(name = "users_roles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_no", referencedColumnName = "no")
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    @Column
    private RoleEnum role;

    @Builder
    public UserRole(Long id, User user, RoleEnum role) {
        this.id = id;
        this.user = user;
        this.role = role;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
