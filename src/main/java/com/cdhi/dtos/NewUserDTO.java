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

    @NotNull(message = "'Email' cannot be null")
    @NotEmpty(message = "'Name' is required")
    private String name;

    @NotNull(message = "'Email' cannot be null")
    @NotEmpty(message = "'Email' is required")
    @Email(message = "Invalid Email")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "'Password' cannot be null")
    @NotEmpty(message = "'Password' is required")
    private String password;
}
