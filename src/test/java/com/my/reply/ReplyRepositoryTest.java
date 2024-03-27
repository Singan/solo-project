package com.my.reply;

import com.my.board.BoardRepository;
import com.my.board.vo.Board;
import com.my.board.vo.BoardInsertDto;
import com.my.reply.vo.Reply;
import com.my.user.UserRepository;
import com.my.user.vo.User;
import com.my.user.vo.UserDetailsDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ReplyRepositoryTest {
    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    BoardRepository boardRepository;
    UserDetailsDto userDetails ;
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
        userDetails = new UserDetailsDto(user);
    }
    @Test
    @DisplayName("댓글 입력 성공")
    @Transactional
    void replyInsertSuccess() {
        //given
        Reply reply = Reply.builder().boardNo(board.getId()).content("댓글 입력 테스트").writer(userDetails.getNo()).build();
        //when
        Reply replyTest = replyRepository.save(reply);
        //then
        Assertions.assertEquals(reply.getContent() , replyTest.getContent());
        Assertions.assertEquals(reply.getBoard() , replyTest.getBoard());


    }
    @Test
    @DisplayName("댓글 수정 성공")
    @Transactional
    void replyUpdateSuccess() {
        //given
        Reply reply = replyRepository.findById(10L).get();
        String testContent = reply.getContent() + "1";
        replyRepository.flush();
        //when
        reply.setContent(testContent);
        replyRepository.save(reply);
        //then
        Assertions.assertEquals(testContent , reply.getContent());

    }


}
