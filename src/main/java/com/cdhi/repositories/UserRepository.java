package com.cdhi.repositories;

import com.cdhi.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional(readOnly=true)
    User findByEmail(String email);

    @Transactional(readOnly=true)
    List<User> findDistinctByNameContainingIgnoreCase(String name);

    @Transactional(readOnly = true)
    Page<User> findDistinctByNameContainingIgnoreCase(String name, Pageable pageRequest);
}
