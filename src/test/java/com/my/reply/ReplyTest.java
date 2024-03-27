package com.my.reply;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.board.BoardRepository;
import com.my.board.vo.Board;
import com.my.config.jwt.JwtProvider;
import com.my.reply.vo.ReplyInsertDto;
import com.my.reply.vo.ReplyUpdateDto;
import com.my.user.UserRepository;
import com.my.user.vo.User;
import com.my.user.vo.UserDetailsDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Slf4j
public class ReplyTest {
    @Autowired
    MockMvc mockMvc;
    String url = "/reply";
    @Autowired
    ObjectMapper mapper;

    @Autowired
    UserRepository userRepository;
    @Autowired
    BoardRepository boardRepository;

    String token;
    @Autowired
    JwtProvider jwtProvider;
    Board board;
    @BeforeEach
    @Rollback
    void init() {
        User user = User.builder()
                .name("테스트 유저")
                .id("테스트 아이디")
                .pw("테스트 비밀번호")
                .type("네이버").build();
        userRepository.save(user);
        board = Board.builder().title("테스트 글 제목").content("테스트 글 내용").writer(user.getNo()).build();
        boardRepository.save(board);
        token = jwtProvider.createToken(user);

    }
    @Test
    @DisplayName("댓글 저장 컨트롤러 테스트")

    void replyInsert() throws Exception {
        //given
        ReplyInsertDto replyInsertDto = getReplyDto();
        String requestBody = mapper.writeValueAsString(replyInsertDto);

        //when
        ResultActions result =mockMvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON)
                .content(requestBody).header("X-AUTH-TOKEN", token));

        //then
        result.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("댓글 업데이트 컨트롤러 테스트")

    void replyUpdate() throws Exception {

        //given
        ReplyUpdateDto replyUpdateDto = new ReplyUpdateDto(1L,"댓글 수정 테스트");
        String requestBody = mapper.writeValueAsString(replyUpdateDto);

        //when
        ResultActions result =mockMvc.perform(MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON)
                .content(requestBody).header("X-AUTH-TOKEN", token));

        //then
        result.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

    }
    private ReplyInsertDto getReplyDto(){
        return new ReplyInsertDto(2L,"테스트 확인");
    }

}
