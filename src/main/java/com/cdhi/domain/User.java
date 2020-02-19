package com.cdhi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;

//   @ManyToMany
//   @JoinTable(name = "USER_BOARD", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "board_id"))
//   private Set<Board> boards = new HashSet<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Board> myBoards = new ArrayList<>();

    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public Set<Board> getBoards() {
//        return boards;
//    }
//
//    public void setBoards(Set<Board> boards) {
//        this.boards = boards;
//    }

    public List<Board> getMyBoards() {
        return myBoards;
    }

    public void setMyBoards(List<Board> myBoards) {
        this.myBoards = myBoards;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
