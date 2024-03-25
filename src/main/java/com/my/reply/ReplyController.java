package com.my.reply;

import com.my.aop.LogClass;
import com.my.reply.vo.ReplyInsertDto;
import com.my.reply.vo.ReplyUpdateDto;
import com.my.user.vo.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
@LogClass
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping
    public void replyInsert(@AuthenticationPrincipal UserDetailsDto userDetailsDto, @RequestBody ReplyInsertDto replyInsertDto){
        replyService.replyInsert(replyInsertDto , userDetailsDto);
    }
    @PutMapping
    public void replyUpdate(@AuthenticationPrincipal UserDetailsDto userDetailsDto, @RequestBody ReplyUpdateDto replyUpdateDto){
        replyService.replyUpdate(replyUpdateDto , userDetailsDto);
    }
}
