package com.my.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.user.vo.User;
import com.my.user.vo.UserDetailsDto;
import com.my.user.vo.UserJoinDto;
import com.my.user.vo.UserLoginDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class UserTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    String id = "타이틀";
    String pw = "콘텐트";
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @BeforeEach
    void init() {
        //미리 준비된 유저 계정 객체 생성
        User user = User.builder()
                .id(id)
                .pw(passwordEncoder.encode(pw))
                .name("init user")
                .build();
        userRepository.save(user);
    }


    @Test
    @DisplayName("유저 회원가입 성공")
    void userJoinSuccess() throws Exception {
        //given
        UserJoinDto userJoinDto = new UserJoinDto("새로운 유저" , "비밀번호","이름","이메일","010-0000-0000");
        String body = objectMapper.writeValueAsString(userJoinDto);
        //when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));

        //then
        result.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("유저 회원가입 실패")
    void userJoinFail() throws Exception {
        //given
        UserJoinDto userJoinDto = new UserJoinDto(id, pw,"이름","email","phone" );
        String body = objectMapper.writeValueAsString(userJoinDto);
        //when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body));
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
                MockMvcRequestBuilders.post("/users/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body));
        //then
        result.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }
    @Test
    @DisplayName("유저 로그인 실패(존재하지 않는 사용자)")
    void userLoginFailNotFound() throws Exception{
        //given
        UserLoginDto userLoginDto = new UserLoginDto(id + "12313123" , pw);
        String body = objectMapper.writeValueAsString(userLoginDto);

        //when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/users/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body));
        //then
        result.andExpect(MockMvcResultMatchers.status().is4xxClientError()).andDo(MockMvcResultHandlers.print());
    }
    @Test
    @DisplayName("유저 로그인 실패(비밀번호 불일치)")
    void userLoginFailPw() throws Exception{
        //given
        UserLoginDto userLoginDto = new UserLoginDto(id, pw + "x");
        String body = objectMapper.writeValueAsString(userLoginDto);

        //when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/users/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body));
        //then
        result.andExpect(MockMvcResultMatchers.status().is4xxClientError()).andDo(MockMvcResultHandlers.print());
    }
}
