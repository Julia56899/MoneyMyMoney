package com.moneymymoney.transaction;

import com.moneymymoney.card.Card;
import com.moneymymoney.category.ExpenseCategory;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    //генерируем айди транзакции
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   //айди транзакции
    private Double sum;   //сумма операции
    private LocalDate date; //дата операции
    private String type; //тип операции - доход/расход

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private ExpenseCategory category;
    private String description;

    public Transaction() {}
    //контрукторы зис обязательно
    public Transaction(Double sum, String description, LocalDate date, String type, Card card, ExpenseCategory category) {
        this.sum = sum;
        this.description = description;
        this.date = date;
        this.type = type;
        this.card = card;
        this.category = category;
    }

    //геттеры и сеттеры
    public Long getId() {
        return id;
    } public void setId(Long id) {
        this.id = id;
    } public Double getSum() {
        return sum;
    }
    public void setSum(Double sum) {
        this.sum = sum;
    } public LocalDate getDate() {
        return date;
    } public void setDate(LocalDate date) {
        this.date = date;
    }  public String getType() {
        return  type;
    } public void setType(String type) {
        this.type = type;
    } public Card getCard() {
        return  card;
    } public void setCard(Card card) {
        this.card = card;
    }public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public ExpenseCategory getCategory() {
        return category;
    }
    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }
}
//заметка