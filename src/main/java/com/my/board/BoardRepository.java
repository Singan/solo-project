package com.my.board;

import com.my.board.vo.Board;
import com.my.board.vo.BoardListViewDto;
import com.my.user.vo.User;
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


    @Query("select distinct b from Board b  " +
            "left join fetch b.writer w " +
            "left join fetch b.replyList r " +
            "left join fetch r.writer wr " +
            "where b.id = :no ")
    Optional<Board> findByIdWithAndReplyList(@Param("no") Long no);

    @Modifying
    void deleteByIdAndWriter(@Param("no")Long no, @Param("writer") User writer);
    
    //왜 DELETE를 하는데 SELECT가 나가는가?
    //DeleteExecution 클래스에서 doExecute 메서드를 통해 삭제 조건에 부합하는 엔티티 목록을 SELECT 한다.
    //여러 SELECT 쿼리가 나가는 이유는 무엇인가?
    //엔티티가 삭제될 때 연관관계의 엔티티도 삭제하기 위해서다.
    //해결법을 찾아야함
}
