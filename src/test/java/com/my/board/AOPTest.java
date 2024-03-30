package com.my.board;

import com.my.aop.LogTrace;
import com.my.board.vo.BoardInsertDto;
import com.my.user.UserRepository;
import com.my.user.UserService;
import com.my.user.vo.User;
import com.my.user.vo.UserDetailsDto;
import com.my.user.vo.UserJoinDto;
import com.my.user.vo.UserLoginDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Slf4j
@Import(LogTrace.class)
public class AOPTest {
    @Autowired
    BoardService boardService;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @BeforeEach
    void init() throws Exception {
        userService.userJoin(new UserJoinDto("id","pw","name","type"));
    }
    @Test
    void testBoardService(){
        boardService.boardInsert(
                new BoardInsertDto("타이틀" , "컨텐트") ,
                new UserDetailsDto(userRepository.findUserById("id")));
    }
}
