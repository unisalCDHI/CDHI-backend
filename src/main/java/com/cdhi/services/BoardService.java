package com.cdhi.services;

import com.cdhi.domain.Board;
import com.cdhi.domain.User;
import com.cdhi.domain.enums.Background;
import com.cdhi.dtos.BoardDTO;
import com.cdhi.dtos.NewBoardDTO;
import com.cdhi.repositories.BoardRepository;
import com.cdhi.repositories.UserRepository;
import com.cdhi.security.UserSS;
import com.cdhi.services.exceptions.AuthorizationException;
import com.cdhi.services.exceptions.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Transactional
    public void addUserInBoard(Integer boardId, Integer userId) {
        Resolver.isMe(userId);
        BoardDTO boardDTO = findOne(boardId);
        Board board = repo.getOne(boardDTO.getId());
        User user = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException("Não foi encontrado um usuário com o id: " + userId));
        Resolver.isUserToAddAlreadyInBoard(user, board);

        board.getUsers().add(user);
        user.getBoards().add(board);

        userRepository.save(user);
        repo.save(board);
    }

    @Transactional
    public void removeUserFromBoard(Integer boardId, Integer userId) {
        Resolver.isMe(userId);
        BoardDTO boardDTO = findOne(boardId);
        Board board = repo.getOne(boardDTO.getId());
        Resolver.isMyBoard(board); // check if user is owner

        User user = userRepository.findById(userId).orElseThrow(() -> new ObjectNotFoundException("Não foi encontrado um usuário com o id: " + userId));
        Resolver.isUserToRemoveNotInBoard(user, board);

        board.getUsers().removeIf(u -> u.getId().equals(user.getId()));
        user.getBoards().removeIf(b -> b.getId().equals(board.getId()));

        userRepository.save(user);
        repo.save(board);
    }

    @Transactional
    public void delete(Integer boardId) {
        List<User> usersToSave = new ArrayList<>();

        BoardDTO boardDTO = findOne(boardId);
        Board board = repo.getOne(boardId);
        Resolver.isMyBoard(board);

        for(User user : board.getUsers()) {
            user.getBoards().removeIf(b -> b.getId().equals(boardId));
            usersToSave.add(user);
        }
        User u = userService.findOne(board.getOwner().getId());
        u.getMyBoards().removeIf(b -> b.getId().equals(boardId));
        usersToSave.add(u);

        board.getUsers().clear();
        board.setOwner(null);

        userRepository.saveAll(usersToSave);
        board = repo.save(board);

        repo.deleteById(board.getId());
    }

    @Transactional
    public BoardDTO findOne(Integer id) {
            Board board = repo.findById(id).orElseThrow(() ->
                    new ObjectNotFoundException("Não foi encontrado Quadro com id: " + id));
            Resolver.isUserInBoard(board); // verifies if user is in board he's trying to get
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

    @Transactional
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

    @Transactional
    public void leave(Integer boardId) {
        BoardDTO boardDTO = findOne(boardId);
        Board board = repo.getOne(boardDTO.getId());

        UserSS userSS = UserService.authenticated();
        Resolver.amINotTheOwner(board, userSS.getId());
        User user = userService.findOne(userSS.getId());
        Resolver.isUserToRemoveNotInBoard(user, board);

        board.getUsers().removeIf(u -> u.getId().equals(user.getId()));
        user.getBoards().removeIf(b -> b.getId().equals(board.getId()));

        userRepository.save(user);
        repo.save(board);

    }

    @Transactional
    public Board save(Integer boardId, NewBoardDTO newBoardDTO) {
        BoardDTO boardDTO = findOne(boardId);
        Board board = repo.getOne(boardId);
        Resolver.isMyBoard(board);

        board.setName(newBoardDTO.getName());
        board.setDescription(newBoardDTO.getDescription());

        return repo.save(board);
    }

    @Transactional
    public Page<BoardDTO> findAllMyByPage(String name, Integer page, Integer size, String orderBy, String direction) {
        UserSS userSS = UserService.authenticated();
        User user = userService.findOne(userSS.getId());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        return repo.findDistinctByNameContainingIgnoreCaseAndOwner_id(name, pageRequest, user.getId()).map(BoardDTO::new);
    }

    @Transactional
    public Page<BoardDTO> findAllByPage(String name, Integer page, Integer size, String orderBy, String direction) {
        UserSS userSS = UserService.authenticated();
        User user = userService.findOne(userSS.getId());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        List<BoardDTO> boards = user.getBoards().stream()
                .map(BoardDTO::new)
                .filter(boardDTO -> boardDTO.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        Page<BoardDTO> boardsPage = new PageImpl<>(boards, pageRequest, boards.size());
        return boardsPage;
    }

    @Transactional
    public void changeBackground(Integer boardId, Background background) {
        BoardDTO boardDTO = findOne(boardId);
        Board board = repo.getOne(boardId);
        Resolver.isUserInBoard(board);
        board.setBackground(background);
        repo.save(board);
    }

//    public List<UserDTO> findAll(String name) {
//        return repo.findDistinctByNameContainingIgnoreCase(name).stream().map(UserDTO::new).collect(Collectors.toList());
//    }
}
