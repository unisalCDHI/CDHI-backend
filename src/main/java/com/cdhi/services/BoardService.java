package com.cdhi.services;

import com.cdhi.domain.Board;
import com.cdhi.domain.User;
import com.cdhi.domain.enums.Profile;
import com.cdhi.dtos.BoardDTO;
import com.cdhi.dtos.NewBoardDTO;
import com.cdhi.dtos.UserDTO;
import com.cdhi.repositories.BoardRepository;
import com.cdhi.repositories.UserRepository;
import com.cdhi.security.UserSS;
import com.cdhi.services.exceptions.AuthorizationException;
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

    @Autowired
    UserRepository userRepository;

    private boolean isUserInBoard(Board board) {
        UserSS user = UserService.authenticated();
        if (user==null || !user.hasRole(Profile.ADMIN) && board.getUsers().stream().noneMatch(u -> u.getId().equals(user.getId()))) {
            throw new AuthorizationException("Acesso negado, você não participa deste quadro.");
        }
        return true;
    }

    public BoardDTO findOne(Integer id) {
            Board board = repo.findById(id).orElseThrow(() ->
                    new ObjectNotFoundException("There's no board with id: " + id));
            isUserInBoard(board); // verifies if user is in board he's trying to get
            return toDTO(board);
    }

    private BoardDTO toDTO(Board board) {
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

    public Board create(NewBoardDTO newBoardDTO) {
        UserSS userSS = UserService.authenticated();
        if (userSS==null) {
            throw new AuthorizationException("Você precisa estar logado para criar um quadro");
        }

        Board board = toBoard(newBoardDTO);
        User user = userService.findOne(userSS.getId());
        board.setOwner(user);
        board.getUsers().add(user);
        Board savedBoard = repo.save(board);

        user.getMyBoards().add(savedBoard);
        user.getBoards().add(savedBoard);
        userRepository.save(user);

        return savedBoard;
    }

    private Board toBoard(NewBoardDTO newBoardDTO) {
        return new Board(newBoardDTO.getName(), null , newBoardDTO.getDescription());
    }

//    public List<UserDTO> findAll(String name) {
//        return repo.findDistinctByNameContainingIgnoreCase(name).stream().map(UserDTO::new).collect(Collectors.toList());
//    }
}
