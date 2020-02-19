package com.cdhi.services;

import com.cdhi.domain.Board;
import com.cdhi.domain.User;
import com.cdhi.repositories.BoardRepository;
import com.cdhi.repositories.UserRepository;
import com.cdhi.services.exceptions.ObjectNotFoundException;
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
        userRepository.saveAll(Arrays.asList(
                new User("Caio", "caiosilveiranunes@piririm.com")
        ));

        User user1 = new User("Jorge", "jorgesilva@gmail.com");

        Board board1 = new Board("Board 1", user1);
        user1.getMyBoards().add(board1);
//        user1.getBoards().add(board1);
//
//        board1.getUsers().add(user1);

        userRepository.save(user1);
        boardRepository.save(board1);
    }
}
