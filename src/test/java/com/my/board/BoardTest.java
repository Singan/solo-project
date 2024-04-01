package com.my.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.board.vo.BoardInsertDto;
import com.my.config.jwt.JwtProvider;
import com.my.user.vo.User;
import com.my.user.vo.UserJoinDto;
import com.my.user.vo.UserLoginDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    String title = " 글 제목 ";
    String content = " 글 제목 ";

    @Autowired
    JwtProvider jwtProvider;

    String token;

    @BeforeEach
    void token() {
        token = jwtProvider.createToken(User.builder().no(1L).build());
    }


    @Test
    @DisplayName("글쓰기")
    @Rollback
    void boardInsert() throws Exception {
        //given
        BoardInsertDto boardInsertDto = new BoardInsertDto("타이틀", "콘텐트");
        String body = objectMapper.writeValueAsString(boardInsertDto);
        //when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/board")
                        .content(body)
                        .header("X-AUTH-TOKEN", token)
        );

        //then
        result.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

    }
}
