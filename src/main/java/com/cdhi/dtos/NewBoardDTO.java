package com.cdhi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class NewBoardDTO {

    @NotNull(message = "'Nome' não pode ser nulo")
    @NotEmpty(message = "'Nome' é obrigatório")
    @Length(min = 0, max=250, message = "Nome deve ter pelo menos 1 caracter (max.:250)")
    private String name;

    private String description;
}
