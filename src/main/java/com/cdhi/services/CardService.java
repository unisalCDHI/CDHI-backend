package com.cdhi.services;

import com.cdhi.domain.Board;
import com.cdhi.domain.Card;
import com.cdhi.domain.User;
import com.cdhi.dtos.CardDTO;
import com.cdhi.dtos.NewCardDTO;
import com.cdhi.repositories.BoardRepository;
import com.cdhi.repositories.CardRepository;
import com.cdhi.repositories.UserRepository;
import com.cdhi.services.exceptions.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @Autowired
    UserRepository userRepository;

    @Transactional
    public List<CardDTO> findAll(Integer boardId) {
        Board board = boardRepository.getOne(boardService.findOne(boardId).getId());
        Resolver.isUserInBoard(board);
        List<Card> cards = board.getCards();
        return cards.stream().map(CardDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public Card findOne(Integer cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() ->
                new ObjectNotFoundException("Não foi encontrado um Cartão com id: " + cardId));
        Resolver.isUserInBoard(card.getBoard());
        return card;
    }

    @Transactional
    public Card create(NewCardDTO newCardDTO) {
        newCardDTO.setId(null);
        Board board = boardRepository.getOne(boardService.findOne(newCardDTO.getBoardId()).getId());
        Card card = toObject(newCardDTO, board);
        Resolver.isUserInBoard(board);

        card.setBoard(board);
        for (User user : card.getUsers()) {
            user.getCards().add(card);
        }
        Card c = cardRepository.save(card);
        userRepository.saveAll(card.getUsers());
        return c;
    }

    @Transactional
    private Card toObject(NewCardDTO newCardDTO, Board board) {
        Card card = new Card(newCardDTO.getColumn(), newCardDTO.getSize(), newCardDTO.getName(), newCardDTO.getDescription(), newCardDTO.getStart_date(), newCardDTO.getEnd_date());
        for (Integer id : newCardDTO.getUsersIds()) {
            User user = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Não foi encontrado um usuário com o id: " + id));
            if (board.getUsers().contains(user))
                card.getUsers().add(user);
            else
                throw new ObjectNotFoundException("O usuário com id: "+ user.getId()+" não foi encontrado no quadro informado");
        }
        return card;
    }

    @Transactional
    public void delete(Integer id) {
        Card card = findOne(id);
        cardRepository.deleteById(id);
    }

    @Transactional
    public Card save(Integer id, NewCardDTO newCardDTO) {
        Card card = findOne(id);
        Set<User> usersOld = card.getUsers();

        newCardDTO.setId(null);
        Board board = card.getBoard();

        Card upCard = toObject(newCardDTO, board);

        card.setName(upCard.getName());
        card.setColumn(upCard.getColumn());
        card.setDescription(upCard.getDescription());
        card.setSize(upCard.getSize());
        card.setStart_date(upCard.getStart_date());
        card.setEnd_date(upCard.getEnd_date());
        card.setUsers(upCard.getUsers());

        Set<User> usersToSave = new HashSet<>();
        for (User user : usersOld) {
            user.getCards().removeIf(c -> c.getId().equals(card.getId()));
            usersToSave.add(user);
        }
        userRepository.saveAll(usersToSave);
        Card card1 = cardRepository.save(card);

        for (User user : card1.getUsers()) {
            user.getCards().add(card);
        }
        userRepository.saveAll(card.getUsers());
        return card1;
    }
}
