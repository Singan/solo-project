package com.my.reply;

import com.my.board.vo.Board;
import com.my.reply.vo.Reply;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @EntityGraph(attributePaths = {"writer"})
    Optional<Reply> findById(Long id);

//    @Modifying
//    void updateById(Reply reply);
}
