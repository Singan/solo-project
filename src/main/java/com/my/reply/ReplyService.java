package com.my.reply;

import com.my.aop.LogClass;
import com.my.board.BoardService;
import com.my.reply.vo.Reply;
import com.my.reply.vo.ReplyInsertDto;
import com.my.user.vo.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@LogClass
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

}
