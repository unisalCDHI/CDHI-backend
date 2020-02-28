package com.cdhi.dtos;

import com.cdhi.domain.Card;
import com.cdhi.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class BoardDTO {

    private Integer id;

    @NotNull(message = "'Email' cannot be null")
    @NotEmpty(message = "'Name' is required")
    private String name;

    @Lob
    private String description;

    @NotNull(message = "'Owner' cannot be null")
    @NotEmpty(message = "'Owner' is required")
    private UserDTO owner;

    private Set<UserDTO> users = new HashSet<>();

    private List<Card> cards = new ArrayList<>();
}
