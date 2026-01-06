 MoneyMyMoney — Управление личными финансами
 О проекте: MoneyMyMoney — это современное приложение для учета личных финансов, разработанное на Spring Boot. Приложение помогает контролировать доходы и расходы, анализировать финансовые привычки и управлять банковскими картами.

 Ключевые особенности
1. Учет операций — добавление доходов и расходов с автоматическим расчетом баланса
2. Категорийный анализ — группировка операций по категориям для понятной статистики
3. Мультикарточность — управление несколькими банковскими картами с отдельными балансами
4. Безопасное хранение— шифрование паролей и защита персональных данных

Быстрый старт:

Предварительные требования:
- Java 17 или выше
- Maven 3.8+
- PostgreSQL 13+ (работает на порту 5432)
- Postman для тестирования API

Запуск приложения:
1. Клонировать репозиторий
git clone https://github.com/julia56899/MoneyMyMoney.git
cd MoneyMyMoney

2. Создать базу данных PostgreSQL
sudo -u postgres createdb moneymymoney
 3. Собрать и запустить проект
mvn clean package
mvn spring-boot:run

Приложение будет доступно по адресу:http://localhost:8080
 Структура проекта:
MoneyMyMoney/
├── src/main/java/com/moneymymoney/
│   ├── user/              # Пользователи
│   │   ├── User.java                 (Entity)
│   │   ├── UserController.java       (Controller)
│   │   ├── UserService.java          (Service)
│   │   ├── UserRepository.java       (Repository)
│   │   ├── UserResponseDTO.java      (DTO для ответа)
│   │   └── DTO.java                  (DTO для регистрации)
│   │
│   ├── transaction/       # Транзакции
│   │   ├── Transaction.java          (Entity)
│   │   ├── TransactionController.java (Controller)
│   │   ├── TransactionService.java    (Service)
│   │   ├── TransactionRepository.java (Repository)
│   │   └── TransactionDTO.java        (DTO)
│   │
│   ├── card/             # Банковские карты
│   │   ├── Card.java                 (Entity)
│   │   └── CardRepository.java       (Repository)
│   │
│   ├── category/         # Категории расходов
│   │   ├── ExpenseCategory.java      (Entity)
│   │   └── CategoryExpenseRepository (Repository)
│   │
│   └── MoneyMyMoneyApplication.java  # Главный класс
├── src/main/resources/
│   └── application.properties        # Конфигурация
├── pom.xml                           # Зависимости Maven
└── README.md                         # Документация

 API Endpoints:

Пользователи (UserController):

| `POST` | `/users/add` | Добавить пользователя |
| `GET` | `/users/all` | Получить всех пользователей |
| `GET` | `/users/{email}` | Найти пользователя по email |
| `POST` | `/users/register` | Регистрация с картой |

💰 Транзакции (TransactionController)

| `POST` | `/transactions/add` | Добавить транзакцию |
| `GET` | `/transactions/balance/{cardId}` | Получить баланс карты |
| `GET` | `/transactions/history/{cardId}` | История операций карты |
| `GET` | `/transactions/transactions/stats/expense/{cardId}` | Статистика расходов по категориям |
| `GET` | `/transactions/transactions/stats/income/{cardId}` | Статистика доходов по категориям |

 Тестирование в Postman:
 1. Регистрация пользователя с картой

POST http://localhost:8080/users/register
Content-Type: application/json
{
    "name": "Юлия Петрова",
    "email": "julia@example.com",
    "login": "julia56899",
    "password": "myPassword123",
    "cardNumber": "1234567890123456",
    "cardType": "VISA"
}
Ожидаемый ответ:
```json
{
    "id": 1,
    "name": "Юлия Петрова",
    "email": "julia@example.com",
    "login": "julia56899"
}
2. Добавление дохода (INCOME)

POST http://localhost:8080/transactions/add
Content-Type: application/json

{
    "sum": 50000.00,
    "description": "Зарплата за декабрь",
    "type": "INCOME",
    "cardId": 1,
    "categoryId": 1
}
Ожидаемый ответ:
```json
{
    "id": 1,
    "sum": 50000.00,
    "description": "Зарплата за декабрь",
    "type": "INCOME",
    "cardId": 1,
    "categoryId": 1,
    "date": "2024-12-05"
}
 3. Добавление расхода (EXPENSE)
POST http://localhost:8080/transactions/add
Content-Type: application/json

{
    "sum": 2500.00,
    "description": "Покупка продуктов",
    "type": "EXPENSE",
    "cardId": 1,
    "categoryId": 3
}
Ожидаемый ответ:
```json
{
    "id": 2,
    "sum": 2500.00,
    "description": "Покупка продуктов",
    "type": "EXPENSE",
    "cardId": 1,
    "categoryId": 3,
    "date": "2024-12-05"
}
 4. Проверка баланса карты

GET http://localhost:8080/transactions/balance/1

Ответ: `47500.00`

 5. Получение истории операций
GET http://localhost:8080/transactions/history/1
 6. Статистика расходов по категориям

GET http://localhost:8080/transactions/transactions/stats/expense/1

**Ожидаемый ответ:**
```json
{
    "FOOD": 2500.00,
    "TRANSPORT": 1500.00
}
7. Статистика доходов по категориям
GET http://localhost:8080/transactions/transactions/stats/income/1
Ожидаемый ответ:
```json
{
    "SALARY": 50000.00,
    "FREELANCE": 15000.00
}
Конфигурация:
 application.properties:
spring.datasource.url=jdbc:postgresql://172.17.0.1:5432/moneymymoney

spring.datasource.username=mymyu
spring.datasource.password=mypassmy
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
logging.level.com.moneymymoney.user=DEBUG
logging.level.org.springframework.web=DEBUG

 Технологический стек
- Backend:Java 17, Spring Boot 3
- База данных: PostgreSQL, Spring Data JPA
- Безопасность: BCryptPasswordEncoder
- Сборка:Maven
- Тестирование: Postman

Проект демонстрирует:
- Полный цикл разработки Spring Boot приложения
- Работу с PostgreSQL через Spring Data JPA
- Реализацию REST API с валидацией
- Безопасное хранение данных (шифрование паролей)
- Чистую архитектуру и разделение ответственности

 Контакты:
Автор: Юлия Гусак  
Email: gusak.julia233@gmail.com  
GitHub:[julia56899](https://github.com/julia56899)  
Репозиторий: [MoneyMyMoney](https://github.com/julia56899/MoneyMyMoney)

Проект обновлен: Декабрь 2025 год
Технологии: Java 17 • Spring Boot 3 • PostgreSQL • Maven
