package com.cdhi.services;

import com.cdhi.domain.Board;
import com.cdhi.domain.Card;
import com.cdhi.repositories.BoardRepository;
import com.cdhi.repositories.CardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    public List<Card> findAll(Integer boardId) {
        Board board = boardRepository.getOne(boardService.findOne(boardId).getId());
        Resolver.isUserInBoard(board);
        return board.getCards();
    }
}
