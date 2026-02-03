package com.moneymymoney.transaction;


import com.moneymymoney.card.Card;
import com.moneymymoney.card.CardRepository;
import com.moneymymoney.category.CategoryExpenseRepository;
import com.moneymymoney.category.ExpenseCategory;

// JUnit
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

// Mockito
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

// Java
import java.util.*;


//подключаем мокито к джиюнит, джиюнит уже активен - аннотация тест
@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private CardRepository cardRepository;
    @Mock
    private CategoryExpenseRepository categoryExpenseRepository;
    @InjectMocks
    private TransactionService transactionService;
    //первый тест - добавление транзакции
    @Test  //название метода - метод_сценарий ожидаемыйрезультат

    void addTransaction_WhenHaveTransactionOfUser_SaveTransaction() {
//создаем тестовую карту
        Card testCard = new Card();
        testCard.setId(1L);
        //создаем тестовую категорию
        ExpenseCategory testCategory = new ExpenseCategory();
        testCategory.setId(1L);

        Transaction testTransaction = new Transaction();
        testTransaction.setSum(1000.0);
        testTransaction.setType("INCOME");
        testTransaction.setDescription("Зарплата");
testTransaction.setCard(testCard);
testTransaction.setCategory(testCategory);
        //дальше настраиваем моки - когда ищут по айди что то -
        when(cardRepository.findById(1L)).thenReturn(Optional.of(testCard));
        when(categoryExpenseRepository.findById(1L)).thenReturn (Optional.of(testCategory));
        when(transactionRepository.findByCardId(1L)).thenReturn(List.of());
        when(transactionRepository.save(any(Transaction.class))).thenReturn (testTransaction);


        //получаем результат карты, категории и суммы
        Transaction result = transactionService.addTransaction(1000.0, "Зарплата","INCOME", 1L, 1L);

        //проверки
//ассерт  -проверки состояния
        //метод вообще что-то вернул?
        assertNotNull(result);
        //сравниваем сумму
        assertEquals(1000.0, result.getSum());
        assertEquals("INCOME", result.getType());
        assertEquals(1L, result.getCard().getId());

        //дальше проверки взаимодействия - верифи,
        verify(transactionRepository).save(any(Transaction.class));
        verify(categoryExpenseRepository).findById(1L);
        verify(cardRepository).findById(1L);

    }


    @Test
    void getCardBalance_WhenGetCardBalance_CardBalance() {

        Transaction t1 = new Transaction();
        t1.setType("INCOME");
        t1.setSum(1000.0);
        Transaction t2 =new Transaction();
        t2.setType("EXPENSE");
        t2.setSum(500.0);

        Double numberTest = 500.0;

        when(transactionRepository.findByCardId(1L)).thenReturn(List.of(t1,t2));

        Double answer = transactionService.getCardBalance(1L);
        //сравниваем
        assertEquals(numberTest,answer);

        verify(transactionRepository).findByCardId(1L);


    }

    @Test

    void getCardTransactions_allTransactionsOfUser_ListOfTransactions() {

        Transaction t1 = new Transaction();
        t1.setType("INCOME");
        t1.setSum(1000.0);
        Transaction t2 = new Transaction();
        t2.setType("EXPENSE");
        t2.setSum(400.0);
        Transaction t3 = new Transaction();
        t3.setType("EXPENSE");
        t3.setSum(100.00);

        List<Transaction> testTransactions = List.of(t1, t2, t3);
        //мокаю
        when(transactionRepository.findByCardId(1L)).thenReturn(testTransactions);

        //дальше вызываю сервис с этими транзакциями
        List<Transaction> result = transactionService.getCardTransactions(1L);
        //сравниваю это ли выдало
        assertEquals(result, testTransactions);
        verify(transactionRepository).findByCardId(1L);


    }
    @Test
    void getCategoryStatistics_GetSumOfMoneyEveryCategoryExpense_getSumOfCategoryExpense() {
//делаем три категории расхродов
        ExpenseCategory testCategoryOne = new ExpenseCategory();
        testCategoryOne.setId(1L);
        testCategoryOne.setName("Еда");

        ExpenseCategory testCategoryTwo = new ExpenseCategory();
        testCategoryTwo.setId(2L);
        testCategoryTwo.setName("Косметика");

        ExpenseCategory testCategoryThree = new ExpenseCategory();
        testCategoryThree.setId(3L);
        testCategoryThree.setName("Кино");


        Transaction t1 = new Transaction();
        t1.setType("EXPENSE");
        t1.setSum(1000.0);
        t1.setCategory(testCategoryTwo);
        Transaction t2 = new Transaction();
        t2.setType("EXPENSE");
        t2.setSum(400.0);
        t2.setCategory(testCategoryOne);
        Transaction t3 = new Transaction();
        t3.setType("EXPENSE");
        t3.setSum(100.00);
        t3.setCategory(testCategoryThree);


        List<Transaction> transactionsExpense = List.of(t1,t2,t3);
//мокаем в репозиторий
        when(transactionRepository.findByCardId(1L)).thenReturn(transactionsExpense);
        //вызываем из сервиса
        Map<String, Double> result = transactionService.getCategoryStatistics(1L);
//дальше сравниваю что вообще получилось по содержимому и количество штук
        assertEquals(1000.0,result.get("Косметика"));
        assertEquals(400.0, result.get("Еда"));

        assertEquals(3,result.size());
        verify(transactionRepository).findByCardId(1L);


    }
    @Test
    void getCategoryStatisticsTwo_GetSumOfMoneyEveryCategoryIncome_getSumOfCategoryIncome() {
        ExpenseCategory testCategoryOne = new ExpenseCategory();
        testCategoryOne.setId(1L);
        testCategoryOne.setName("Зарплата");

        ExpenseCategory testCategoryTwo = new ExpenseCategory();
        testCategoryTwo.setId(2L);
        testCategoryTwo.setName("Фриланс");

        ExpenseCategory testCategoryThree = new ExpenseCategory();
        testCategoryThree.setId(3L);
        testCategoryThree.setName("Премия");

        //дальше создаем транзакции сами
        Transaction t1 = new Transaction();
        t1.setSum(200.0);
        t1.setType("INCOME");
        t1.setCategory(testCategoryOne);

        Transaction t2 = new Transaction();
        t2.setSum(450.0);
        t2.setType("INCOME");
        t2.setCategory(testCategoryTwo);

        Transaction t3 = new Transaction();
        t3.setSum(300.0);
        t3.setCategory(testCategoryThree);
        t3.setType("INCOME");

        //добавляем все в лист
        List<Transaction> transactions = List.of(t1,t2, t3);

        //мокаем репозиторий
        when(transactionRepository.findByCardId(1L)).thenReturn(transactions);

        //вызываем уже мап из сервиса и проверяем
        Map<String, Double> result = transactionService.getCategoryStatisticsTwo(1L);

        //теперь сравниваем что получилось и взаимодействие
        assertEquals(300.0, result.get("Премия"));
        assertEquals(450.0, result.get("Фриланс"));

        verify(transactionRepository).findByCardId(1L);



    }

}
