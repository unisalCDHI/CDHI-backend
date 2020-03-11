package com.cdhi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class NewBoardDTO {

    @NotNull(message = "'Name' cannot be null")
    @NotEmpty(message = "'Name' is required")
    private String name;

    @Lob
    private String description;
}
