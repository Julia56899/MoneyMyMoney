package com.moneymymoney.transaction;


import com.moneymymoney.card.Card;
import com.moneymymoney.category.ExpenseCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TransactionService transactionService;

    @Test
    void addTransaction_addTransactionOfUserWithDTO_transactionAddBD() throws Exception {

        Card testCard = new Card();
        testCard.setId(1L);
        //создаем тестовую категорию
        ExpenseCategory testCategory = new ExpenseCategory();
        testCategory.setId(1L);

        Transaction testTransaction = new Transaction();
        testTransaction.setSum(200.0);
        testTransaction.setDescription("Кино");
        testTransaction.setType("Expense");
        testTransaction.setCard(testCard);
        testTransaction.setCategory(testCategory);


        //мокаем транзактионконтролер
        when(transactionService.addTransaction(
                200.0,
                "Кино",
                "Expense",
                1L,
                1L
        )).thenReturn(testTransaction);

        //вызваем эндпоинт
        mockMvc.perform(
                post("/transactions/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sum\":200.0,\"description\":\"Кино\",\"type\":\"Expense\",\"cardId\":1,\"categoryId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category.id").value(1L))
                .andExpect(jsonPath("$.description").value("Кино"))
                .andExpect(jsonPath("$.sum").value(200.0));

        verify(transactionService).addTransaction(200.0,
                "Кино",
                "Expense",
                1L,
                1L);


    }

    @Test
    void getCardBalance_getBalanceOFExpenseAndIncomeTransactions_balanceCardOfUser() throws Exception{
        //создаем тестовые транзакции
        Transaction testTransactionOne = new Transaction();
        testTransactionOne.setSum(5000.0);
        testTransactionOne.setType("INCOME");


        Transaction testTransactionTwo = new Transaction();
        testTransactionTwo.setSum(300.0);
        testTransactionTwo.setType("INCOME");


        Transaction testTransactionThree = new Transaction();
        testTransactionThree.setSum(1000.0);
        testTransactionThree.setType("EXPENSE");

        Double balance = 4300.0;

        //мокаем тразнактионсервис
        when(transactionService.getCardBalance(1L)).thenReturn(balance);

        //вызываем эндпоинт
        mockMvc.perform(get("/transactions/balance/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("4300.0"));

        verify(transactionService).getCardBalance(1L);

    }
    @Test
    void  getCardTransaction_arrayAllTransactionsOfCard_TransactionsOfCard() throws Exception {
        //моковые транзакции
        Transaction testTransactionOne = new Transaction();
        testTransactionOne.setSum(5000.0);
        testTransactionOne.setType("INCOME");

        Transaction testTransactionTwo = new Transaction();
        testTransactionTwo.setSum(300.0);
        testTransactionTwo.setType("INCOME");

        Transaction testTransactionThree = new Transaction();
        testTransactionThree.setSum(1000.0);
        testTransactionThree.setType("EXPENSE");

        List<Transaction> testTransactions = List.of(testTransactionOne, testTransactionTwo, testTransactionThree);



        //мокаем транзактион сервис
        when(transactionService.getCardTransactions(1L)).thenReturn(testTransactions);

        //вызываем эндпоинт
        mockMvc.perform(get("/transactions/history/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sum").value(5000.0))
                .andExpect(jsonPath("$[0].type").value("INCOME"))
                .andExpect(jsonPath("$[1].sum").value(300.0))
                .andExpect(jsonPath("$.length()").value(3));



        verify(transactionService).getCardTransactions(1L);


    }
    @Test
    void getCategoryExpense_StatisticsTransactionsOfExpense_TransactionOfExpense() throws  Exception {
        //мокаем мапу
        Map<String, Double> testMap = Map.of(
                "Еда", 1000.0,
                "Кино", 200.0
        );
        //мокаем сервис
        when(transactionService.getCategoryStatistics(1L)).thenReturn(testMap);

        //вызываем эндпоинт
        mockMvc.perform(get("/transactions/transactions/stats/expense/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Еда").value(1000.0))
                .andExpect(jsonPath("$.Кино").value(200.0));

        verify(transactionService).getCategoryStatistics(1L);


    }

    @Test
    void getCategoryIncome_StatisticsTransactionsOfIncome_TransactionOfIncome() throws  Exception {

        //мокаем мапу
        Map<String, Double> testMap = Map.of(
                "Фриланс", 5000.0,
                "Зарплата", 3000.0
        );
        //мокаем сервис
        when(transactionService.getCategoryStatisticsTwo(1L)).thenReturn(testMap);

        //вызываем эндпоинт
        mockMvc.perform(get("/transactions/transactions/stats/income/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Фриланс").value(5000.0))
                .andExpect(jsonPath("$.Зарплата").value(3000.0));

        verify(transactionService).getCategoryStatisticsTwo(1L);


    }



}
