package com.my.user;

import com.my.aop.LogClass;
import com.my.user.vo.User;
import com.my.user.vo.UserJoinDto;
import com.my.user.vo.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@LogClass
public class UserController {


    private final UserService userService;


    @PostMapping
    public void userJoin(@RequestBody UserJoinDto userJoinDto) {
        userService.userJoin(userJoinDto);
    }

    @PostMapping("/login")
    public String userLogin(@RequestBody UserLoginDto userLoginDto) {
        return userService.userLogin(userLoginDto);
    }
}
