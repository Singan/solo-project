package com.my.reply;

import com.my.aop.LogClass;
import com.my.board.BoardService;
import com.my.board.exception.BoardErrorCode;
import com.my.board.exception.BoardException;
import com.my.reply.vo.Reply;
import com.my.reply.vo.ReplyInsertDto;
import com.my.reply.vo.ReplyUpdateDto;
import com.my.user.vo.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {
    private final ReplyRepository replyRepository;
    @Transactional
    public void replyInsert(ReplyInsertDto replyInsertDto, UserDetailsDto userDetailsDto) {

        Reply reply = Reply.builder()
                .boardNo(replyInsertDto.boardNo())
                .writer(userDetailsDto.getNo())
                .content(replyInsertDto.content()).build();


        replyRepository.save(reply);
    }

    private boolean authChk(Long replyNo , Long userNo){
        try{
            Reply reply = findOneReply(replyNo);
            if(reply.getWriter().getNo() == userNo){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            throw e;
        }
    }

    private Reply findOneReply(Long replyNo){
        return replyRepository.findById(replyNo).orElseThrow(() -> new BoardException(BoardErrorCode.BOARD_NOT_FOUND));
    }
    @Transactional
    public void replyUpdate(ReplyUpdateDto replyUpdateDto, UserDetailsDto userDetailsDto) {
        Long replyNo = replyUpdateDto.replyNo();
        authChk(replyNo , userDetailsDto.getNo());


        Reply reply = Reply.builder()
                .replyNo(replyNo)
                .content(replyUpdateDto.content()).build();


        replyRepository.save(reply);
    }
}
