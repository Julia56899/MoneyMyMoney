package com.moneymymoney.user;

import com.fasterxml.jackson.annotation.JsonProperty;

//это форма для регистрации пользователя на платформе
public class DTO {
    //для регистрации необходимо имя,емайл, логин
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("login")
    private String login;
    @JsonProperty("password")
    private String password;

    @JsonProperty("cardNumber")
    private String cardNumber;
    @JsonProperty("cardType")
    private String cardType;

    public DTO () {}
    public DTO (String name, String email, String login, String password,String cardNumber,String cardType) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.cardNumber = cardNumber;
        this.cardType = cardType;

    }
    //геттер - получить, сеттер - изменить
    //пишем геттеры и сетеры чтобы все этии данные пользователь мог если чо изменить
    public String getName() {
        return name;
    } public void setName(String name) {
        this.name = name;
    } public String getEmail() {
        return email;
    } public void setEmail(String email) {
        this.email = email;
    } public  String getLogin() {
        return login;
    } public void setLogin (String login) {
        this.login = login;
    } public String getPassword() {
        return password;
    } public void setPassword (String password) {
        this.password = password;
    } public  String getCardNumber() {
        return  cardNumber;
    } public void setCardNumber (String cardNumber) {
        this.cardNumber = cardNumber;
    } public String getCardType() {
        return  cardType;
    } public  void setCardType(String cardType) {
        this.cardType = cardType;
    }



}
