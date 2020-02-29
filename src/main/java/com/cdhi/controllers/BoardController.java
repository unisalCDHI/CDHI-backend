package com.cdhi.controllers;

import com.cdhi.domain.Board;
import com.cdhi.dtos.BoardDTO;
import com.cdhi.services.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Board Controller")
@RestController
@RequestMapping(value = "boards")
public class BoardController {

    @Autowired
    BoardService service;

    @ApiOperation(value = "Get Board by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<BoardDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findOne(id));
    }

//    @ApiOperation(value = "Get Board by USER ID")
//    @GetMapping(value = "/user/{id}")
//    public ResponseEntity<BoardDTO> getAllBoardsFromUser(@PathVariable Integer id) {
//        return ResponseEntity.status(HttpStatus.OK).body(service.findOne(id));
//    }
}
