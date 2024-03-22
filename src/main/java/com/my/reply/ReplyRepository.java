package com.my.reply;

import com.my.board.vo.Board;
import com.my.reply.vo.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
