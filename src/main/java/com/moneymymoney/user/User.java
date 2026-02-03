package com.moneymymoney.user;
//тут будут лежать "данные из базы" - как таблицы в бд
//здесь будет не форма регистрации а скелет пользователя в системе
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moneymymoney.card.Card;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
//автогенерация айди
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String login;
    //для регистрации логина
    private String password;

    //связь с картами
    @OneToMany (mappedBy = "com/moneymymoney/user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Card> cards; //список карт


    public User() {}

    public User(String name, String email, String login, String password) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;

    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public List<Card> getCards() { return cards; }
    public void setCards(List<Card> cards) { this.cards = cards; }
}


