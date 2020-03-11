package com.cdhi.dtos;

import com.cdhi.domain.Card;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class BoardDTO implements Serializable {

    private Integer id;

    @NotNull(message = "'Name' cannot be null")
    @NotEmpty(message = "'Name' is required")
    private String name;

    @Lob
    private String description;

    @NotNull(message = "'Owner' cannot be null")
    @NotEmpty(message = "'Owner' is required")
    private UserDTO owner;

    @NotNull(message = "'Users' cannot be null")
    private List<UserDTO> users;

    private List<Card> cards;
}
