package com.cdhi.services;

import com.cdhi.domain.Board;
import com.cdhi.domain.Card;
import com.cdhi.repositories.BoardRepository;
import com.cdhi.repositories.CardRepository;
import com.cdhi.services.exceptions.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Card findOne(Integer cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() ->
                new ObjectNotFoundException("There's no card with id: " + cardId));
        Resolver.isUserInBoard(card.getBoard());
        return card;
    }
}
