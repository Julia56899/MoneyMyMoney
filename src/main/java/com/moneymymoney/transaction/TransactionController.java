package com.moneymymoney.transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    //добавить операцию
    //получить баланс карты
    //все операции вывести
    @PostMapping("/add")
    public Transaction addTransaction(@RequestBody TransactionDTO dto) {
        return transactionService.addTransaction(
                dto.getSum(),
                dto.getDescription(),
                dto.getType(),
                dto.getCardId(),
                dto.getCategoryId()
        );
    }

    @GetMapping("/balance/{cardId}")
    public Double getCardBalance(@PathVariable Long cardId) {
        return transactionService.getCardBalance(cardId);
    }

    @GetMapping("/history/{cardId}")
    public List<Transaction> getCardTransactions(@PathVariable Long cardId) {
        return transactionService.getCardTransactions(cardId);
    }


    @GetMapping("/transactions/stats/expense/{cardId}")
    public Map<String, Double> getCategoryExpence(@PathVariable Long cardId) {
        return transactionService.getCategoryStatistics(cardId);
    }

    @GetMapping("/transactions/stats/income/{cardId}")
    public Map<String, Double> getCategoryIncome(@PathVariable Long cardId) {
        return transactionService.getCategoryStatisticsTwo(cardId);
    }
}
