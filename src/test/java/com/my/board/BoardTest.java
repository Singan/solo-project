package com.my.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.board.vo.BoardInsertDto;
import com.my.board.vo.BoardUpdateDto;
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
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BoardTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    int boardNo = 5;

    @Autowired
    JwtProvider jwtProvider;

    String token;

    @BeforeEach
    void token() {
        //미리 준비된 유저 계정 객체 생성
        User user = User.builder()
                .no(1L)
                .name("name")
                .type("naver")
                .build();
        token = jwtProvider.createToken(user);
    }


    @Test
    @DisplayName("글쓰기")
    void boardInsert() throws Exception {
        //given
        BoardInsertDto boardInsertDto = new BoardInsertDto("글 추가 테스트 코드 제목", "글 추가 테스트코드 내용");
        String body = objectMapper.writeValueAsString(boardInsertDto);
        //when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/board")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", token)
        );

        //then
        result.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("글 수정")
    void boardUpdate() throws Exception {
        //given
        BoardUpdateDto boardUpdateDto = new BoardUpdateDto("타이틀 수정", "콘텐트 수정");
        String body = objectMapper.writeValueAsString(boardUpdateDto);
        //when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.put("/board/"+boardNo)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", token)
        );

        //then
        result.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("글 삭제")
    void boardDelete() throws Exception {
        //given


        //when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.delete("/board/"+boardNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", token)
        );

        //then
        result.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

    }
}
