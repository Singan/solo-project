package com.my.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.board.vo.Board;
import com.my.board.vo.BoardInsertDto;
import com.my.board.vo.BoardUpdateDto;
import com.my.config.jwt.JwtProvider;
import com.my.reply.vo.Reply;
import com.my.user.UserRepository;
import com.my.user.vo.User;
import com.my.user.vo.UserJoinDto;
import com.my.user.vo.UserLoginDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Slf4j
@ActiveProfiles("test")
public class BoardTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtProvider jwtProvider;
    String token;
    String url = "/board";
    Board board;
    private Board createBoard(User user) {
        Board board = Board.builder()
                .writer(user.getNo())
                .dateTime(LocalDateTime.now())
                .build();
        return board;
    }

    @BeforeEach
    void init() {
        //미리 준비된 유저 계정 객체 생성
        User user = User.builder()
                .id("id")
                .pw("pw")
                .name("name")
                .build();
        //Board.writer 에 들어갈 User 미리 생성 후 저장
        // 삭제 수정을 위한 board 미리 DB에 저장
        userRepository.save(user);
        board = createBoard(user);
        boardRepository.save(board);

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
                MockMvcRequestBuilders.post(url)
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
        System.out.println(board.getId());
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.put(url + "/" + board.getId())
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
                MockMvcRequestBuilders.delete(url + "/" + board.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-AUTH-TOKEN", token)
        );

        //then
        result.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("글 상세 조회")
    void boardDetail() throws Exception {
        //given


        //when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get(url + "/" +board.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("글 리스트 조회")
    void boardList() throws Exception {
        //given
        int pageNo = 0;

        //when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get(url + "?pageNo=" + pageNo)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

    }
}
