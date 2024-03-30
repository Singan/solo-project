package com.my.user;

import com.my.aop.LogClass;
import com.my.user.vo.User;
import com.my.user.vo.UserJoinDto;
import com.my.user.vo.UserLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@LogClass
public class UserController {


    private final UserService userService;


    @PostMapping
    public ResponseEntity userJoin(@RequestBody UserJoinDto userJoinDto) {
        try {
            userService.userJoin(userJoinDto);
            return ResponseEntity.ok().body("성공적으로 회원가입 하였습니다.");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public String userLogin(@RequestBody UserLoginDto userLoginDto) {
        return userService.userLogin(userLoginDto);
    }
}
