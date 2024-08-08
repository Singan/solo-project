package com.my.user.vo;

import com.my.user.vo.extend.UserViewDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsDto extends UserViewDto implements UserDetails {

    public UserDetailsDto(User user) {
        super(user.getNo(), user.getName(), user.getUserRoles());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream().map(userRole -> userRole.getRole()).toList();
    }

    @Override
    public String getPassword() {
        return getName();
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
