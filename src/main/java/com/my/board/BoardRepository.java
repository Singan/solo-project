package com.my.board;

import com.my.board.vo.Board;
import com.my.board.vo.BoardListViewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    @Query("select new com.my.board.vo.BoardListViewDto(b.id , b.title) from Board b")
    Page<BoardListViewDto> findPageableList(Pageable pageable);

}
