package com.my.user.vo.extend;

import com.my.user.role.RoleEnum;
import com.my.user.role.UserRole;
import lombok.Data;

import java.util.Collection;

@Data
public class UserViewDto {
    private final Long no;

    private final String name;
    private final Collection<UserRole> roles;
}
