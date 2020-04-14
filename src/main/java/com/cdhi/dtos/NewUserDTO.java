package com.cdhi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class NewUserDTO implements Serializable {

    private Integer id;

    @NotNull(message = "'Nome' não pode ser nulo")
    @NotEmpty(message = "'Nome' é obrigatório")
    private String name;

    @NotNull(message = "'Email' não pode ser nulo")
    @NotEmpty(message = "'Email' é obrigatório")
    @Email(message = "Email inválido <email@exemplo.com>")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "'Senha' não pode ser nulo")
    @NotEmpty(message = "'Senha' é obrigatório")
    private String password;
}
