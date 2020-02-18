package com.cdhi.controllers;

import com.cdhi.domain.User;
import com.cdhi.repositories.UserRepository;
import com.cdhi.services.exceptions.ObjectAlreadyExistsException;
import com.cdhi.services.exceptions.ObjectNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "User Controller")
@RestController
@RequestMapping(value = "users")
public class UserController {

//    @Autowired
//    UserService repo;

    @Autowired
    UserRepository repo;

    @ApiOperation(value = "Get User by email")
    @GetMapping(value = "/{email}")
    public ResponseEntity<User> getByEmail(@PathVariable String email) {
        if (repo.findByEmail(email) != null)
         return ResponseEntity.status(HttpStatus.OK).body(repo.findByEmail(email));
        else
            throw new ObjectNotFoundException("Email Not Found");
    }
}
