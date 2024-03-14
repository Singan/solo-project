package com.my.user;

import com.my.user.vo.UserJoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping
    public void userJoin(@RequestBody UserJoinDto userJoinDto){
        userService.userJoin(userJoinDto);
    }
}
