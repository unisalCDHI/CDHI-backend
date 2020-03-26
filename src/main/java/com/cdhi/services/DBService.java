package com.cdhi.services;

import com.cdhi.domain.Board;
import com.cdhi.domain.Card;
import com.cdhi.domain.User;
import com.cdhi.domain.enums.Column;
import com.cdhi.repositories.BoardRepository;
import com.cdhi.repositories.CardRepository;
import com.cdhi.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@Service
@Slf4j
public class DBService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    BCryptPasswordEncoder CRYPTER;

    public void instantiateTestDatabase() throws ParseException {

        User user1 = new User("Jorge", "jorgesilva@gmail.com", CRYPTER.encode("123456"));
        User user2 = new User("Caio", "caiosilveiranunes@piririm.com", CRYPTER.encode("123123"));
        User user3 = new User("Paulinho", "paulinho@pau.linho", CRYPTER.encode("456456"));
        user1.setEnabled(true);
        user2.setEnabled(true);
        user3.setEnabled(true);
        user1.set_key(null);
        user2.set_key(null);
        user3.set_key(null);

        Board board1 = new Board("Board 1", user1);
        Board board2 = new Board("Board 2", user1);
        Board board3 = new Board("Board 3", user2);
        Board board4 = new Board("Board do teste", user3, "too long for columnValue too long for columnValue too long for columnValue too long for columnValue too long for columnValue too long for columnaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaacolumnaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaacolumnaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaacolumnaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaacolumnaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaacolumnaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaacolumnaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaacolumnaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaacolumnaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaacolumnaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaacolumnaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaacolumnaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        Card card1 = new Card(Column.BACKLOG, 0, "Card 1", "jaz aqui o card 1", Calendar.getInstance().getTime(), Calendar.getInstance().getTime());
        Card card2 = new Card(Column.TODO, 0, "Card 2", "este é o card 2", Calendar.getInstance().getTime(), Calendar.getInstance().getTime());

        board4.getCards().addAll(Arrays.asList(card1, card2));
        card1.setBoard(board4);
        card2.setBoard(board4);

        card1.getUsers().add(user1);
        card2.getUsers().addAll(Arrays.asList(user2, user3));

        user1.getMyBoards().add(board1);
        user1.getBoards().add(board1);
        board1.getUsers().add(user1);
        board1.setOwner(user1);

        user1.getMyBoards().add(board2);
        user1.getBoards().add(board2);
        board2.getUsers().add(user1);
        board2.setOwner(user1);

        user2.getMyBoards().add(board3);
        user2.getBoards().add(board3);
        board3.getUsers().add(user2);
        board3.setOwner(user2);

        user2.getBoards().add(board2);
        board2.getUsers().add(user2);

        user3.getMyBoards().add(board4);
        user3.getBoards().addAll(Arrays.asList(board1, board2, board3, board4));
        board1.getUsers().add(user3);
        board2.getUsers().add(user3);
        board3.getUsers().add(user3);
        board4.getUsers().add(user3);
        board4.setOwner(user3);

        userRepository.saveAll(Arrays.asList(user1, user2, user3));
        boardRepository.saveAll(Arrays.asList(board1, board2, board3, board4));
        cardRepository.saveAll(Arrays.asList(card1, card2));

        log.info("Cards do User {}: {}", user2, user2.getCards());
    }
}
