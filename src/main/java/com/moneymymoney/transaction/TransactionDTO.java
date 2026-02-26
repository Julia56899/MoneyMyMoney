package com.moneymymoney.transaction;

public class TransactionDTO {
    private Double sum;
    private String description;
    private String type;
    private Long cardId;
    private Long categoryId;

    public TransactionDTO() {
    }

    public TransactionDTO(double sum, String description, String type, Long cardId, Long categoryId) {
        this.sum = sum;
        this.description = description;
        this.type = type;
        this.cardId = cardId;
        this.categoryId = categoryId;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
//заметка гит на трназактион дто 