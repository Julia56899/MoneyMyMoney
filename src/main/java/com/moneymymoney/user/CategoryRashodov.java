package com.moneymymoney.user;
import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class CategoryRashodov {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
private String type;
private String color;

    public CategoryRashodov() {}
//конструкторы
    public CategoryRashodov(String name, String type, String color) {
        this.name = name;
        this.type = type;
        this.color = color;
    }
    //геттеры и сеттеры
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    } public String getName() {
        return name;
    } public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
}
//добавляем в транзактион связь с категори