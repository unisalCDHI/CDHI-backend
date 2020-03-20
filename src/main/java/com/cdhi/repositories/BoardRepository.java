package com.cdhi.repositories;

import com.cdhi.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Transactional(readOnly = true)
    Page<Board> findDistinctByNameContainingIgnoreCaseAndOwner_id(String name, Pageable pageRequest, Integer owner_id);

}
