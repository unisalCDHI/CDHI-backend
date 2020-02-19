package com.cdhi.dtos;

import com.cdhi.domain.User;
import io.swagger.models.auth.In;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserDTO implements Serializable {

    private Integer id;

    @NotNull(message = "'Email' cannot be null")
    @NotEmpty(message = "'Name' is required")
    private String name;

    @NotNull(message = "'Email' cannot be null")
    @NotEmpty(message = "'Email' is required")
    @Email(message = "Invalid Email")
    private String email;

    public UserDTO() {
    }

  public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
  }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
