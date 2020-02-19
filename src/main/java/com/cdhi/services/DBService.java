package com.cdhi.services;

import com.cdhi.domain.Board;
import com.cdhi.domain.User;
import com.cdhi.repositories.BoardRepository;
import com.cdhi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Arrays;

@Service
public class DBService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    public void instantiateTestDatabase() throws ParseException {

        User user1 = new User("Jorge", "jorgesilva@gmail.com");
        User user2 = new User("Caio", "caiosilveiranunes@piririm.com");

        Board board1 = new Board("Board 1", user1);
        Board board2 = new Board("Board 2", user1);
        Board board3 = new Board("Board 3", user2);
        user1.getMyBoards().add(board1);
        user1.getMyBoards().add(board2);
        user2.getMyBoards().add(board3);
        user2.getBoards().add(board2);

        userRepository.saveAll(Arrays.asList(user1, user2));
        boardRepository.saveAll(Arrays.asList(board1, board2, board3));
    }
}
