package com.my.board;

import com.my.board.vo.Board;
import com.my.board.vo.BoardListViewDto;
import com.my.user.vo.User;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select new com.my.board.vo.BoardListViewDto(b.id , b.title) from Board b")
    Page<BoardListViewDto> findPageableList(Pageable pageable);

    @EntityGraph(attributePaths = {"writer" })
    Optional<Board> findById(Long id);


    @Query("select distinct b from Board b " +
            " join fetch b.replyList reply  " +
            " join fetch b.writer wr " +
            " join fetch reply.writer rwr " +
            "where b.id = :no ")
    Optional<Board> findByIdWithAndReplyList(@Param("no") Long no);

    @Modifying
    void deleteByIdAndWriter(@Param("no")Long no, @Param("writer") User writer);

}
