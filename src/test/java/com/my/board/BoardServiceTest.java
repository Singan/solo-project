package com.my.board;


import com.my.board.exception.BoardErrorCode;
import com.my.board.exception.BoardException;
import com.my.board.vo.*;
import com.my.reply.vo.Reply;
import com.my.user.exception.UserErrorCode;
import com.my.user.exception.UserException;
import com.my.user.vo.User;
import com.my.user.vo.UserDetailsDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.security.sasl.AuthenticationException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @BeforeEach
    public void init() {
    }

    String title = "타이틀";
    String content = "컨텐츠";

    private User createUser() {
        return User.builder()
                .no(1L)
                .id("test_id")
                .pw("test_pw")
                .name("test_name")
                .build();
    }

    private Board createBoard(User user) {
        Reply r = Reply.builder().content("댓글 컨텐츠").writer(user.getNo()).build();
        Board board = Board.builder()
                .id(1L)
                .writer(user.getNo())
                .title(title)
                .content(content)
                .dateTime(LocalDateTime.now())
                .build();
        board.addReply(r);
        return board;
    }

    @Nested
    @DisplayName("성공 케이스")
    class Success {
        @Test
        @DisplayName("게시글 삽입")
        public void testBoardInsert() {
            BoardInsertDto boardInsertDto = new BoardInsertDto("Title", "Content");
            User user = createUser();
            UserDetailsDto userDetailsDto = new UserDetailsDto(user);

            Board board = createBoard(user);

            when(boardRepository.save(any(Board.class))).thenReturn(board);

            Long boardId = boardService.boardInsert(boardInsertDto, userDetailsDto);

            assertNotNull(boardId);
            assertEquals(1L, boardId);
        }

        @Test
        @DisplayName("게시글 수정")
        public void testBoardUpdate() throws AuthenticationException {
            Long boardNo = 1L;
            BoardUpdateDto boardUpdateDto = new BoardUpdateDto("New Title", "New Content");
            User user = createUser();
            UserDetailsDto userDetailsDto = new UserDetailsDto(user);
            Board board = createBoard(user);

            when(boardRepository.findByIdWithAndReplyList(boardNo)).thenReturn(Optional.of(board));

            Long updatedBoardId = boardService.boardUpdate(boardUpdateDto, userDetailsDto, boardNo);

            assertNotNull(updatedBoardId);
            assertEquals(boardNo, updatedBoardId);
        }
        @Test
        @DisplayName("게시글 리스트 조회 케이스")

        public void testBoardList() {
            Pageable pageable = PageRequest.of(0, 10);
            List<BoardListViewDto> boardList = Collections.singletonList(new BoardListViewDto(1L, "Title", LocalDateTime.now()));
            SliceImpl<BoardListViewDto> slice = new SliceImpl<>(boardList, pageable, false);

            when(boardRepository.findPageableList(pageable)).thenReturn(slice);

            ListResult result = boardService.boardList(pageable);

            assertNotNull(result);
            assertEquals(1, result.list().size());
        }


        @Test
        @DisplayName("게시글 상세 조회")
        public void testBoardDetail() {
            Long boardNo = 1L;
            User user = createUser();
            Board board = createBoard(user);

            when(boardRepository.findByIdWithAndReplyList(boardNo)).thenReturn(Optional.of(board));

            BoardViewDto result = boardService.boardDetail(boardNo);
            assertNotNull(result);
            assertEquals(title, result.title());
            assertEquals(1, result.replyList().count());
        }

        @Test
        @DisplayName("게시글 삭제")
        public void testBoardDelete() {
            Long boardNo = 1L;
            User user = createUser();
            UserDetailsDto userDetailsDto = new UserDetailsDto(user);
            Board board = createBoard(user);

            when(boardRepository.findByIdWithAndReplyList(boardNo)).thenReturn(Optional.of(board));

            doNothing().when(boardRepository).deleteById(boardNo);

            boardService.boardDelete(boardNo, userDetailsDto);

            verify(boardRepository, times(1)).deleteById(boardNo);
        }
    }
    @Nested
    @DisplayName("실패 케이스")
    class Fail {
        @Test
        @DisplayName("존재하지 않는 게시글")
        public void testBoardDetailNotFound() {
            Long boardNo = 1L;

            when(boardRepository.findByIdWithAndReplyList(boardNo)).thenReturn(Optional.empty());

            BoardException exception = assertThrows(BoardException.class, () -> {
                boardService.boardDetail(boardNo);
            });

            assertEquals(BoardErrorCode.BOARD_NOT_FOUND, exception.getErrorCode());
        }


        @Test
        @DisplayName("게시글 삭제 권한 없음")
        public void testBoardDeleteAccessDenied() {
            Long boardNo = 1L;
            User user = createUser();
            UserDetailsDto userDetailsDto = new UserDetailsDto(User.builder()
                    .no(2L)
                    .id("test_id")
                    .pw("test_pw")
                    .name("test_name")
                    .build());
            Board board = createBoard(user);

            when(boardRepository.findByIdWithAndReplyList(boardNo)).thenReturn(Optional.of(board));

            UserException exception = assertThrows(UserException.class, () -> {
                boardService.boardDelete(boardNo, userDetailsDto);
            });

            assertEquals(UserErrorCode.USER_ACCESS_DENIED, exception.getErrorCode());
        }



        @Test
        @DisplayName("게시글 수정 권한 없음")
        public void testBoardUpdateAccessDenied() {
            Long boardNo = 1L;
            BoardUpdateDto boardUpdateDto = new BoardUpdateDto("New Title", "New Content");
            User user = createUser();
            Board board = createBoard(user);
            UserDetailsDto userDetailsDto = new UserDetailsDto(User.builder()
                    .no(2L)
                    .id("test_id")
                    .pw("test_pw")
                    .name("test_name")
                    .build());

            when(boardRepository.findByIdWithAndReplyList(boardNo)).thenReturn(Optional.of(board));

            UserException exception = assertThrows(UserException.class, () -> {
                boardService.boardUpdate(boardUpdateDto, userDetailsDto, 1L);
            });

            assertEquals(UserErrorCode.USER_ACCESS_DENIED, exception.getErrorCode());
        }
    }
}
