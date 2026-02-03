package com.moneymymoney;

import com.moneymymoney.category.CategoryExpenseRepository;
import com.moneymymoney.category.ExpenseCategory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication

public class MoneyMyMoneyApplication {
    public static void main (String[] args) {

        SpringApplication.run(MoneyMyMoneyApplication.class, args);
    }

    @Bean
    @Profile("dev")
    public CommandLineRunner intCategory(CategoryExpenseRepository categoryRas) {
        return args -> {
            if(categoryRas.count() ==0) {
                //если 0 строк в бд - тогда создаем категории в этой бд
                categoryRas.save(new ExpenseCategory("Зарплата", "INCOME", "#4CAF50"));
                categoryRas.save(new ExpenseCategory("Доход","INCOME", "#FFEB3B"));
                categoryRas.save(new ExpenseCategory("Продукты", "EXPENSE", "#FF5722"));
                categoryRas.save(new ExpenseCategory("Транспорт", "EXPENSE", "#2196F3"));
                categoryRas.save(new ExpenseCategory("Развлечения", "EXPENSE", "#9C27B0"));
                categoryRas.save(new ExpenseCategory("Одежда", "EXPENSE", "#FF9800"));
                categoryRas.save(new ExpenseCategory("Жилье", "EXPENSE", "#795548"));
                categoryRas.save(new ExpenseCategory("Здоровье", "EXPENSE", "#F44336"));
                categoryRas.save(new ExpenseCategory("Образование", "EXPENSE", "#00BCD4"));
                categoryRas.save(new ExpenseCategory("Рестораны", "EXPENSE", "#E91E63"));
                categoryRas.save(new ExpenseCategory("Красота", "EXPENSE", "#FF4081"));
                categoryRas.save(new ExpenseCategory("Спорт", "EXPENSE", "#4CAF50"));
                categoryRas.save(new ExpenseCategory("Подарки", "EXPENSE", "#FFC107"));
                categoryRas.save(new ExpenseCategory("Техника", "EXPENSE", "#607D8B"));
                categoryRas.save(new ExpenseCategory("Путешествия", "EXPENSE", "#3F51B5"));
                categoryRas.save(new ExpenseCategory("Хобби", "EXPENSE", "#8BC34A"));
                categoryRas.save(new ExpenseCategory("Дети", "EXPENSE", "#FFEB3B"));
                categoryRas.save(new ExpenseCategory("Авто", "EXPENSE", "#FF5722"));
                categoryRas.save(new ExpenseCategory("Связь", "EXPENSE", "#9C27B0"));
                categoryRas.save(new ExpenseCategory("Налоги", "EXPENSE", "#795548"));
                categoryRas.save(new ExpenseCategory("Прочее", "EXPENSE", "#9E9E9E"));

                System.out.println("===Категории созданы!===");


            }
        };
    }



}