package com.my.user.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleEnum {
    USER ("ROLE_USER"),
    ADMIN ("ROLE_ADMIN,ROLE_USER");

    private final String roles;

}