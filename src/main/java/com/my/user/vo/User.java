package com.my.user.vo;

import com.my.user.role.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
    @OneToMany(mappedBy = "user")
    private Collection<UserRole> userRoles = new ArrayList<>();


    private String email;
    private String phone;

    @Builder
    public User(Long no, String id, String pw, String name, String phone, String email, Collection<UserRole> userRoles) {
        this.no = no;
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.userRoles.addAll(userRoles);
    }

    public void addRoles(UserRole userRoles) {
        System.out.println(this.userRoles);
        this.userRoles.add(userRoles);
    }
}
