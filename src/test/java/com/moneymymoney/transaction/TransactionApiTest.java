package com.moneymymoney.transaction;

import com.moneymymoney.card.Card;
import com.moneymymoney.card.CardRepository;
import com.moneymymoney.category.CategoryExpenseRepository;
import com.moneymymoney.category.ExpenseCategory;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")

public class TransactionApiTest {


    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CategoryExpenseRepository categoryExpenseRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private Long cardId;
    private Long categoryId;


    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        Card testCard = new Card("1111222233334444", "DEBIT", 1000.0);
        testCard = cardRepository.save(testCard);
        cardId = testCard.getId();

        ExpenseCategory testCategory = new ExpenseCategory();
        testCategory.setName("Тестовая категория");
        testCategory.setType("EXPENSE");
        testCategory.setColor("#000000");
        testCategory = categoryExpenseRepository.save(testCategory);
        categoryId = testCategory.getId();

        //создание одной тестовой транзакции
        Transaction testTransaction = new Transaction();
        testTransaction.setSum(500.0);
        testTransaction.setDescription("Тестовая транзакция");
        testTransaction.setDate(LocalDate.of(2026,9,1));
        testTransaction.setType("EXPENSE");
        testTransaction.setCard(testCard);
        testTransaction.setCategory(testCategory);
        testTransaction = transactionRepository.save(testTransaction);

    }

    @Test
    void addTransaction_shouldReturnCreatedTransaction() {
        given()
                .contentType("application/json")
                .body("""
                        {
                                      "sum": 200.0,
                                      "description": "Кино",
                                      "type": "EXPENSE",
                                      "cardId": %d,
                                      "categoryId": %d
                                    }
                                     """.formatted(cardId, categoryId)
                )

                .when()
                .post("/transactions/add")
                .then()
                .statusCode(200)
                .body("description", equalTo("Кино"));

    }

    @Test
    void cardBalance_testOfGetCardBalance() {
        given()
                .when()
                .get("/transactions/balance/{cardId}", cardId)
                .then()
                .statusCode(200);

    }

    @Test
    void cardTransactions_testOfGetCardTransactions() {
        given()
                .when()
                .get("/transactions/history/{cardId}",cardId)
                .then()
                .statusCode(200);

    }

    @Test
    void categoryExpense_testOfCategoryExpense() {
        given()
                .when()
                .get("/transactions/stats/expense/{cardId}",cardId)
                .then()
                .statusCode(200);
    }


}



