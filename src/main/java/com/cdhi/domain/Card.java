package com.cdhi.domain;

import com.cdhi.domain.enums.Column;

import javax.persistence.*;
import java.util.*;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer size;
    private String name;
    @Lob
    private String description;
    private Column column;
//    private Set<Comment>
    private Date start_date;
    private Date end_date;

    @ManyToMany(mappedBy = "cards", cascade = CascadeType.DETACH)
    private Set<User> users = new HashSet<>();

    @JoinColumn(name = "board_id")
    @ManyToOne(cascade = CascadeType.DETACH)
    private Board board;

    public Card() {
    }

    public Card(Integer size, String name, String description, Date start_date, Date end_date) {
        this.size = size;
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
