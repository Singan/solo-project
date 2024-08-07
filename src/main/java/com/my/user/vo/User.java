package com.my.user.vo;

import com.my.user.role.UserRole;
import jakarta.persistence.*;
import lombok.*;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

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
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<UserRole> userRoles = new HashSet<>();


    private String email;
    private String phone;

    @Builder
    public User(Long no, String id, String pw, String name, String phone, String email) {
        this.no = no;
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public void addRoles(UserRole userRoles) {
        this.userRoles.add(userRoles);
        userRoles.setUser(this);
    }
    public void setUserRoles(Collection<UserRole> userRoles) {
        userRoles.stream().forEach(this::addRoles);
    }
}
