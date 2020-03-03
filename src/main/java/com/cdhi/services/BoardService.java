package com.cdhi.services;

import com.cdhi.domain.Board;
import com.cdhi.domain.User;
import com.cdhi.dtos.BoardDTO;
import com.cdhi.dtos.UserDTO;
import com.cdhi.repositories.BoardRepository;
import com.cdhi.services.exceptions.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BoardService {
    @Autowired
    BoardRepository repo;

    @Autowired
    UserService userService;

    public BoardDTO findOne(Integer id) {
        return toDTO(repo.findById(id).orElseThrow(() -> new ObjectNotFoundException("There's no board with id: " + id)));
    }

    public BoardDTO toDTO(Board board) {
        System.out.println(board);
        return new BoardDTO(
                board.getId(),
                board.getName(),
                board.getDescription(),
                userService.toDTO(board.getOwner()),
                board.getUsers()
                        .stream().map(user -> userService.toDTO(user)).collect(Collectors.toList()),
                board.getCards());
    }

//    public List<UserDTO> findAll(String name) {
//        return repo.findDistinctByNameContainingIgnoreCase(name).stream().map(UserDTO::new).collect(Collectors.toList());
//    }
}
