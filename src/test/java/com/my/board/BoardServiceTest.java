package com.my.board;


import com.my.board.exception.BoardErrorCode;
import com.my.board.exception.BoardException;
import com.my.board.vo.*;
import com.my.reply.vo.Reply;
import com.my.user.exception.UserErrorCode;
import com.my.user.exception.UserException;
import com.my.user.vo.User;
import com.my.user.vo.UserDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
@AutoConfigureMockMvc
public class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    private User createUser() {
        return User.builder().no(1L).id("test_id").pw("test_pw").name("test_name").build();
    }

    private Board createBoard(Long no) {
        return Board.builder().writer(no).title("타이틀").content("콘텐트").dateTime(LocalDateTime.now()).build();
    }

    @Test
    public void testBoardInsert() {
        BoardInsertDto boardInsertDto = new BoardInsertDto("Title", "Content");
        UserDetailsDto userDetailsDto = new UserDetailsDto(createUser());
        Board board =createBoard(userDetailsDto.getNo());

        when(boardRepository.save(any(Board.class))).thenReturn(board);

        Long boardId = boardService.boardInsert(boardInsertDto, userDetailsDto);

        assertNotNull(boardId);
        assertEquals(1L, boardId);
    }

    @Test
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
    public void testBoardListNotFound() {
        Pageable pageable = PageRequest.of(0, 10);
        SliceImpl<BoardListViewDto> emptySlice = new SliceImpl<>(Collections.emptyList(), pageable, false);

        when(boardRepository.findPageableList(pageable)).thenReturn(emptySlice);

        BoardException exception = assertThrows(BoardException.class, () -> {
            boardService.boardList(pageable);
        });

        assertEquals(BoardErrorCode.BOARD_NOT_FOUND, exception.getErrorCode());
    }

//    @Test
//    public void testBoardDetail() {
//        Long boardNo = 1L;
//        User user = createUser();
//        Board board = createBoard(user.getNo());
//        board.setReplyList(Collections.singletonList(new Reply(1L, board.getId(), user, "");
//
//        when(boardRepository.findByIdWithAndReplyList(boardNo)).thenReturn(Optional.of(board));
//
//        BoardViewDto result = boardService.boardDetail(boardNo);
//
//        assertNotNull(result);
//        assertEquals("Title", result.getTitle());
//        assertEquals(1, result.getReplyList().getReplyCount());
//    }
//
//    @Test
//    public void testBoardDetailNotFound() {
//        Long boardNo = 1L;
//
//        when(boardRepository.findByIdWithAndReplyList(boardNo)).thenReturn(Optional.empty());
//
//        BoardException exception = assertThrows(BoardException.class, () -> {
//            boardService.boardDetail(boardNo);
//        });
//
//        assertEquals(BoardErrorCode.BOARD_NOT_FOUND, exception.getErrorCode());
//    }
//
//    @Test
//    public void testBoardDelete() throws Exception {
//        Long boardNo = 1L;
//        UserDetailsDto userDetailsDto = new UserDetailsDto(1L, "username", "email@example.com", "ROLE_USER");
//        Board board = new Board(boardNo, "Title", "Content", new User(1L, "username", "password"));
//
//        when(boardRepository.findByIdWithAndReplyList(boardNo)).thenReturn(Optional.of(board));
//
//        doNothing().when(boardRepository).deleteById(boardNo);
//
//        boardService.boardDelete(boardNo, userDetailsDto);
//
//        verify(boardRepository, times(1)).deleteById(boardNo);
//    }
//
//    @Test
//    public void testBoardDeleteAccessDenied() {
//        Long boardNo = 1L;
//        UserDetailsDto userDetailsDto = new UserDetailsDto(2L, "username2", "email2@example.com", "ROLE_USER");
//        Board board = new Board(boardNo, "Title", "Content", new User(1L, "username", "password"));
//
//        when(boardRepository.findByIdWithAndReplyList(boardNo)).thenReturn(Optional.of(board));
//
//        UserException exception = assertThrows(UserException.class, () -> {
//            boardService.boardDelete(boardNo, userDetailsDto);
//        });
//
//        assertEquals(UserErrorCode.USER_ACCESS_DENIED, exception.getErrorCode());
//    }
//
//    @Test
//    public void testBoardUpdate() throws AuthenticationException {
//        Long boardNo = 1L;
//        BoardUpdateDto boardUpdateDto = new BoardUpdateDto("New Title", "New Content");
//        UserDetailsDto userDetailsDto = new UserDetailsDto(1L, "username", "email@example.com", "ROLE_USER");
//        Board board = new Board(boardNo, "Title", "Content", new User(1L, "username", "password"));
//
//        when(boardRepository.findByIdWithAndReplyList(boardNo)).thenReturn(Optional.of(board));
//
//        Long updatedBoardId = boardService.boardUpdate(boardUpdateDto, userDetailsDto, boardNo);
//
//        assertNotNull(updatedBoardId);
//        assertEquals(boardNo, updatedBoardId);
//    }
//
//    @Test
//    public void testBoardUpdateAccessDenied() {
//        Long boardNo = 1L;
//        BoardUpdateDto boardUpdateDto = new BoardUpdateDto("New Title", "New Content");
//        UserDetailsDto userDetailsDto = new UserDetailsDto(2L, "username2", "email2@example.com", "ROLE_USER");
//        Board board = new Board(boardNo, "Title", "Content", new User(1L, "username", "password"));
//
//        when(boardRepository.findByIdWithAndReplyList(boardNo)).thenReturn(Optional.of(board));
//
//        UserException exception = assertThrows(UserException.class, () -> {
//            boardService.boardUpdate(boardUpdateDto, userDetailsDto, boardNo);
//        });
//
//        assertEquals(UserErrorCode.USER_ACCESS_DENIED, exception.getErrorCode());
//    }
}
