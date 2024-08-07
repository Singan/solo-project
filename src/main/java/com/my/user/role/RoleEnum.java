package com.my.user.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@RequiredArgsConstructor
public enum RoleEnum implements GrantedAuthority {
    USER ("ROLE_USER"),
    ADMIN ("ROLE_ADMIN");

    private final String roles;

    @Override
    public String getAuthority() {
        return roles;
    }
}