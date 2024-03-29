package com.my.board;

import com.my.aop.LogClass;
import com.my.board.vo.*;
import com.my.reply.vo.Reply;
import com.my.reply.vo.ReplyListDto;
import com.my.reply.vo.ReplyViewDto;
import com.my.user.vo.User;
import com.my.user.vo.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.sasl.AuthenticationException;
import java.util.List;

@Service
@RequiredArgsConstructor
@LogClass
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional

    public Long boardInsert(BoardInsertDto boardDto, UserDetailsDto userDetailsDto) {
        return boardRepository.save(boardDto.createBoard(userDetailsDto)).getId();
    }

    public ListResult boardList(Pageable pageable) {
        Page<BoardListViewDto> boardList = boardRepository.findPageableList(pageable);
        return new ListResult(boardList.getNumber(), boardList.toList());
    }

    public BoardViewDto boardDetail(Long boardNo) {
        Board board = boardFindOneWithReply(boardNo);
        List<Reply> replyList = board.getReplyList();
        ReplyListDto replyListDto = new ReplyListDto(
                replyList.size(),
                replyList.stream().map(reply ->
                        new ReplyViewDto(reply.getId(), reply.getContent(), reply.getWriter().getName())
                ).toList()
        );
        return new BoardViewDto(board.getId(), board.getTitle(), board.getContent(), board.getWriter().getName(),replyListDto );
    }

    @Transactional
    public void boardDelete(Long boardNo, UserDetailsDto userDetailsDto) {

        boardRepository.deleteByIdAndWriter(boardNo, User.builder().no(userDetailsDto.getNo()).build());
    }
    @Transactional
    public Long boardUpdate(BoardUpdateDto boardUpdateDto,UserDetailsDto userDetailsDto) throws AuthenticationException {
        Board board = boardFindOneWithReply(boardUpdateDto.no());
        if(!authCheck(board,userDetailsDto)){
            throw new AuthenticationException("수정 권한이 없습니다.");
        }

        board.boardUpdate(boardUpdateDto.title(), boardUpdateDto.content());
        return board.getId();
    }
    private boolean authCheck(Board board,UserDetailsDto userDetailsDto){
        if(board.getWriter().getNo() != userDetailsDto.getNo()){
            return false;
        }
        return true;
    }
    private Board boardFindOne(Long boardNo) {
        Board board = boardRepository.findById(boardNo).orElseThrow(() -> new RuntimeException("없는 글입니다."));
        return board;
    }

    private Board boardFindOneWithReply(Long boardNo) {
        Board board = boardRepository.findByIdWithAndReplyList(boardNo).orElseThrow(() -> new RuntimeException("없는 글입니다."));
        return board;
    }
}
