package com.moneymymoney.transaction;
//в сервисе у меня будет добавление операции, расчет баланса карты, получить все  операции по карте
import java.util.HashMap;
import java.util.List;

import com.moneymymoney.card.Card;
import com.moneymymoney.card.CardRepository;
import com.moneymymoney.category.CategoryExpenseRepository;
import com.moneymymoney.category.ExpenseCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Map;
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final CategoryExpenseRepository categoryExpenseRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              CardRepository cardRepository,
                              CategoryExpenseRepository categoryExpenseRepository) {

        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
        this.categoryExpenseRepository = categoryExpenseRepository;
    }
//три основных метода - добавление операции
    //расчет баланса карты
    //история всех операций


    public Transaction addTransaction(Double sum, String description, String type, Long cardId, Long categoryId) {
//находим карту
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("Card not found"));

        ExpenseCategory categoryExpense = categoryExpenseRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        //создаем транзакцию
        Transaction transaction = new Transaction();
        transaction.setSum(sum);
        transaction.setDescription(description);
        transaction.setCategory(categoryExpense);
        transaction.setDate(LocalDate.now());
        transaction.setCard(card);
        transaction.setType(type);
        //сохраняем
        Double balance = getCardBalance(cardId);
        card.setBalance(balance);
        cardRepository.save(card);
        return transactionRepository.save(transaction);
    }

//дальше расчет баланса карты

    public Double getCardBalance(Long cardId) {
//найти все транзакции по кардайди
        List<Transaction> transactions = transactionRepository.findByCardId(cardId);
        //посчитать сумму
        Double balance = 0.0;
        for (Transaction transaction : transactions) {
            if ("INCOME".equals(transaction.getType())) {
                balance += transaction.getSum();
            }
            if ("EXPENSE".equals(transaction.getType())) {
                balance -= transaction.getSum();
            }

        }
        return balance;
    }

    public List<Transaction> getCardTransactions(Long cardId) {
        return transactionRepository.findByCardId(cardId);

    }

    //метод который делит транзакции на доходы и расходы
    //дальше расходы делит по категориям
    //сумму транзакций  в каждой категории - это все в статистику
    //доходы также аналогично на категории
    //и сумма этих категорий
    public Map<String, Double> getCategoryStatistics(Long cardId) {
        //мне нужно для каждой категории посчитать сумму расходов
        Map<String, Double> stats = new HashMap<>();
        List<Transaction> transactions = transactionRepository.findByCardId(cardId);
        for (Transaction transaction : transactions) {
            if ("EXPENSE".equals(transaction.getType())) {
                String categoryName = transaction.getCategory().getName();
                Double categorySum = transaction.getSum();
                Double totalSum = stats.get(categoryName);
                if (totalSum == null) totalSum = 0.0;
                Double newAnswer = totalSum + categorySum;
                stats.put(categoryName, newAnswer);
            }

        }
        return stats;
    }

    public Map<String, Double> getCategoryStatisticsTwo(Long cardId) {
        Map<String, Double> statsDo = new HashMap<>();
        List<Transaction> transactions = transactionRepository.findByCardId(cardId);
        for (Transaction transaction : transactions) {
            if ("INCOME".equals(transaction.getType())) {
                String categoryDo = transaction.getCategory().getName();
                Double categorySumD = transaction.getSum();
                Double total = statsDo.get(categoryDo);
                if (total == null) total = 0.0;
                Double answe = total + categorySumD;
                statsDo.put(categoryDo, answe);

            }
        }
        return statsDo;
    }
}