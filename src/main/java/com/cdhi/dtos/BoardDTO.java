package com.cdhi.dtos;

import com.cdhi.domain.Board;
import com.cdhi.domain.Card;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class BoardDTO implements Serializable {

    private Integer id;

    @NotNull(message = "'Nome' não pode ser nulo")
    @NotEmpty(message = "'Nome' é obrigatório")
    private String name;

    private String description;

    @NotNull(message = "'Owner' não pode ser nulo")
    @NotEmpty(message = "'Owner' é obrigatório")
    private UserDTO owner;

    @NotNull(message = "'Users' não pode ser nulo")
    private List<UserDTO> users;

    private List<Card> cards;

    public BoardDTO(Board board) {
        this.id = board.getId();
        this.name = board.getName();
        this.description = board.getDescription();
        this.users = board.getUsers().stream().map(UserDTO::new).collect(Collectors.toList());
        this.owner = new UserDTO(board.getOwner());
        this.cards = board.getCards();
    }
}
