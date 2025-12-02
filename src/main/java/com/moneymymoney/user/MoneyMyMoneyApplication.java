package com.moneymymoney.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class MoneyMyMoneyApplication {
    public static void main (String[] args) {

        SpringApplication.run(MoneyMyMoneyApplication.class, args);
    }

    @Bean
    public CommandLineRunner intCategory(CategoryRashodovRepository categoryRas) {
        return args -> {
            if(categoryRas.count() ==0) {
                //если 0 строк в бд - тогда создаем категории в этой бд
                categoryRas.save(new CategoryRashodov("Зарплата", "INCOME", "#4CAF50"));
                categoryRas.save(new CategoryRashodov("Доход","INCOME", "#FFEB3B"));
                categoryRas.save(new CategoryRashodov("Продукты", "EXPENSE", "#FF5722"));
                categoryRas.save(new CategoryRashodov("Транспорт", "EXPENSE", "#2196F3"));
                categoryRas.save(new CategoryRashodov("Развлечения", "EXPENSE", "#9C27B0"));
                categoryRas.save(new CategoryRashodov("Одежда", "EXPENSE", "#FF9800"));
                categoryRas.save(new CategoryRashodov("Жилье", "EXPENSE", "#795548"));
                categoryRas.save(new CategoryRashodov("Здоровье", "EXPENSE", "#F44336"));
                categoryRas.save(new CategoryRashodov("Образование", "EXPENSE", "#00BCD4"));
                categoryRas.save(new CategoryRashodov("Рестораны", "EXPENSE", "#E91E63"));
                categoryRas.save(new CategoryRashodov("Красота", "EXPENSE", "#FF4081"));
                categoryRas.save(new CategoryRashodov("Спорт", "EXPENSE", "#4CAF50"));
                categoryRas.save(new CategoryRashodov("Подарки", "EXPENSE", "#FFC107"));
                categoryRas.save(new CategoryRashodov("Техника", "EXPENSE", "#607D8B"));
                categoryRas.save(new CategoryRashodov("Путешествия", "EXPENSE", "#3F51B5"));
                categoryRas.save(new CategoryRashodov("Хобби", "EXPENSE", "#8BC34A"));
                categoryRas.save(new CategoryRashodov("Дети", "EXPENSE", "#FFEB3B"));
                categoryRas.save(new CategoryRashodov("Авто", "EXPENSE", "#FF5722"));
                categoryRas.save(new CategoryRashodov("Связь", "EXPENSE", "#9C27B0"));
                categoryRas.save(new CategoryRashodov("Налоги", "EXPENSE", "#795548"));
                categoryRas.save(new CategoryRashodov("Прочее", "EXPENSE", "#9E9E9E"));

                System.out.println("===Категории созданы!===");

                
            }
        };
    }



}