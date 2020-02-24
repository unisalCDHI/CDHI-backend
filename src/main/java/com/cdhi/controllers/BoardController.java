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

@Api(value = "Board Controller")
@RestController
@RequestMapping(value = "boards")
public class BoardController {

//    @Autowired
//    BoardService service;
//
//    @ApiOperation(value = "Get Board by id")
//    @GetMapping(value = "/{id}")
//    public ResponseEntity<User> getById(@PathVariable Integer id) {
//        return ResponseEntity.status(HttpStatus.OK).body(service.findOne(id));
//    }
}
