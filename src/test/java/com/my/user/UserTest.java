package com.my.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.user.vo.UserJoinDto;
import com.my.user.vo.UserLoginDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    String id = "타이틀";
    String pw = "콘텐트";

    @Test
    @DisplayName("유저 회원가입 성공")
    @Rollback
    void userJoinSuccess() throws Exception {
        //given
        UserJoinDto userJoinDto = new UserJoinDto("새로운 유저" , "비밀번호","이름" , "없음");
        String body = objectMapper.writeValueAsString(userJoinDto);
        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/user").content(body));

        //then
        result.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("유저 회원가입 실패")
    void userJoinFail() throws Exception {
        //given
        UserJoinDto userJoinDto = new UserJoinDto(id , pw,"이름" , "없음");
        String body = objectMapper.writeValueAsString(userJoinDto);
        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/user").content(body));
        //then
        result.andExpect(MockMvcResultMatchers.status().is4xxClientError()).andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("유저 로그인 성공")
    void userLogin() throws Exception{
        //given
        UserLoginDto userLoginDto = new UserLoginDto(id , pw);
        String body = objectMapper.writeValueAsString(userLoginDto);

        //when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body));
        //then
        result.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }
}
