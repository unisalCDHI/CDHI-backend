package com.cdhi.controllers;

import com.cdhi.domain.Card;
import com.cdhi.dtos.NewCardDTO;
import com.cdhi.services.CardService;
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

@Api(value = "Card Controller")
@RestController
@RequestMapping(value = "cards")
public class CardController {

    @Autowired
    CardService service;

    @ApiOperation(value = "Get Cards from board")
    @GetMapping
    public ResponseEntity<List<Card>> findAll(@RequestParam(value = "board", defaultValue = "") Integer boardId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(boardId));
    }

    @ApiOperation(value = "Get Card")
    @GetMapping(value = "{id}")
    public ResponseEntity<Card> findOne(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findOne(id));
    }

    @ApiOperation(value = "Create Card")
    @PostMapping
    public ResponseEntity<?> create(@RequestParam(value = "board", defaultValue = "") Integer boardId, @RequestBody @Valid NewCardDTO newCardDTO) {
        Card c = service.create(newCardDTO, boardId);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(c.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
