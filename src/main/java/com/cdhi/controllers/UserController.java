package com.cdhi.controllers;

import com.cdhi.domain.User;
import com.cdhi.dtos.UserDTO;
import com.cdhi.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api(value = "User Controller")
@RestController
@RequestMapping(value = "users")
public class UserController {

    @Autowired
    UserService service;

    @ApiOperation(value = "Get User by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findOne(id));
    }

    @ApiOperation(value = "Get Users")
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @ApiOperation(value = "Create User")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UserDTO UserDTO) {
        User u = service.create(UserDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(u.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Edit User")
    @PutMapping(value = "{id}")
    public ResponseEntity<?> save(@RequestBody @Valid UserDTO userDTO, @PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.save(userDTO, id));
    }

    @ApiOperation(value = "Delete User")
    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("User Id: " + id + " deleted successfully!");
    }
}
