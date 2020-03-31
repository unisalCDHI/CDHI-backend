package com.cdhi.dtos;

import com.cdhi.domain.Board;
import com.cdhi.domain.Card;
import com.cdhi.domain.enums.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CardDTO {

    private Integer id;
    private Integer size;
    @Length(min = 0, message = "Nome deve ter pelo menos 1 caracter")
    private String name;
    private String description;
    @NotNull(message = "Parâmetro 'column' não pode ser nulo")
    private Column column;
    private Date start_date;
    private Date end_date;

    private Set<UserDTO> users;
    private Board board;

    public CardDTO(Card card) {
        this.id = card.getId();
        this.size = card.getSize();
        this.name = card.getName();
        this.description = card.getDescription();
        this.column = card.getColumn();
        this.start_date = card.getStart_date();
        this.end_date = card.getEnd_date();
        this.users = card.getUsers().stream().map(UserDTO::new).collect(Collectors.toSet());
        this.board = card.getBoard();
    }
}
