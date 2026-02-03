package com.moneymymoney.category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryExpenseRepository extends JpaRepository<ExpenseCategory, Long> {
}
