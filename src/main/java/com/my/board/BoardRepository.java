package com.my.board;

import com.my.board.vo.Board;
import com.my.board.vo.BoardListViewDto;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select new com.my.board.vo.BoardListViewDto(b.id , b.title , b.dateTime,b.writer.name) from Board b")
    @EntityGraph(attributePaths = {"writer" })
    Slice<BoardListViewDto> findPageableList(Pageable pageable);

    @Query(value = "SELECT COUNT(1) FROM (" +
            "SELECT 1 FROM " +
            " board b WHERE " +
            " b.id < :id ORDER BY b.id DESC limit :n) AS subquery"
            ,nativeQuery = true
    )
    Integer leftPage(@Param("id") Long id, @Param("n") long n);

    @Query("select count(b.id) from Board b")
    Integer countAll();


    @EntityGraph(attributePaths = {"writer" })
    Optional<Board> findById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = {"writer","replyList","replyList.writer"})
    Optional<Board> findBoardById(@Param("no") Long no);

    @Query("update Board set views = views+1 where id = :id")
    @Modifying
    void updateBoardByViewsWithLock(@Param("id") long id);

//    @Modifying
//    @EntityGraph(attributePaths = {"replyList"})
//    Board deleteById(@Param("no")Long no);

}
