package com.cdhi.domain;

import com.cdhi.domain.enums.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity(name = "USER_ENTITY")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @javax.persistence.Column(name="_name")
    private String name;
    @Column(unique = true)
    private String email;
    private Boolean enabled;

    private Date created;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PROFILES")
    private Set<Integer> profiles = new HashSet<>();

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "KEYS")
    private List<String> _key = new ArrayList<>();

    @JsonIgnore
    private String password;

    @ManyToMany
    @JoinTable(name = "USER_BOARD", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "board_id"))
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> myBoards = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "USER_CARD", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "card_id"))
    private Set<Card> cards = new HashSet<>();


    public User() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        setEnabled(false);
        get_key().add(
                random.ints(leftLimit, rightLimit + 1)
                        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                        .limit(targetStringLength)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString()
        );
        addProfile(Profile.USER);
        this.created = new Date(System.currentTimeMillis());
    }

    public User(String name, String email, String password) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        this.name = name;
        this.email = email;
        this.password = password;
        addProfile(Profile.USER);
        setEnabled(false);
        get_key().add(
                random.ints(leftLimit, rightLimit + 1)
                        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                        .limit(targetStringLength)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString()
        );
        this.created = new Date(System.currentTimeMillis());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> get_key() {
        return _key;
    }

    public Date getCreated() {
        return created;
    }

    public void set_key(List<String> _key) {
        this._key = _key;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Profile> getProfiles() {
        return profiles.stream().map(Profile::toEnum).collect(Collectors.toSet());
    }

    public void addProfile(Profile profile) {
        profiles.add(profile.getCod());
    }

    public List<Board> getBoards() {
        return boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

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

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
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

    public void deleteKey() {
        this._key = null;
    }
}
