package com.moneymymoney.transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository  extends  JpaRepository<Transaction,Long>{
    List<Transaction> findByCardId(Long cardId); //по этому айди все операции по карте
}