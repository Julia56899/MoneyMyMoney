MoneyMyMoney — это полнофункциональный личный финансовый трекер на Spring Boot с PostgreSQL. Приложение позволяет отслеживать доходы и расходы, управлять банковскими картами, анализировать расходы по категориям и получать статистику в реальном времени.
Особенности:
•	Учет транзакций (доходы/расходы) с привязкой к картам
•	Автоматический расчет баланса карт при каждой операции
•	Статистика расходов по категориям
•	 Статистика доходов по категориям
•	Регистрация пользователей с созданием карты
•	Безопасность — шифрование паролей BCrypt
•	20+ предустановленных категорий (продукты, транспорт, развлечения и др.)
•	Полное тестирование API через Postman
Технологический стек:
1.Backend:
•	Java 17 — основной язык программирования
•	Spring Boot 3.3.4 — фреймворк для создания приложения
•	Spring Data JPA — работа с базой данных
•	Spring Security Crypto — шифрование паролей
2.База данных:
•	PostgreSQL — это настоящая база данных, где хранятся все данные: пользователи, карты, транзакции, категории. В отличие от временной базы H2 (которая удаляется после остановки программы), PostgreSQL сохраняет всё навсегда — даже если выключить компьютер, все транзакции останутся в базе.
•	Hibernate — это инструмент, который автоматически превращает Java-классы в таблицы базы данных. Можно написать к примеру класс User с полями id, name, email, а Hibernate сам создаёт таблицу user в PostgreSQL и сохраняет туда данные.
Инструменты:
•	Maven — сборка проекта
•	Postman — тестирование API

Структура проекта:
MoneyMyMoney/
│
├── src/main/java/com/moneymymoney/
│   ├── MoneyMyMoneyApplication.java      # Главный файл, запуск приложения
│   │
│   ├── card/                             # Карты банковские
│   │   ├── Card.java                     # Класс карты (id, номер, тип, баланс)
│   │   └── CardRepository.java           # Запросы к базе по картам
│   │
│   ├── category/                         # Категории трат
│   │   ├── ExpenseCategory.java          # Класс категории (id, название, тип)
│   │   └── CategoryExpenseRepository.java # Запросы к базе по категориям
│   │
│   ├── transaction/                      # Транзакции (доходы/расходы)
│   │   ├── Transaction.java              # Класс транзакции
│   │   ├── TransactionController.java    # API: добавление, баланс, статистика
│   │   ├── TransactionDTO.java           # Данные для создания транзакции
│   │   ├── TransactionRepository.java    # Запросы к базе по транзакциям
│   │   └── TransactionService.java       # Логика: добавить, посчитать баланс
│   │
│   └── user/                             # Пользователи
│       ├── User.java                     # Класс пользователя
│       ├── UserController.java           # API: регистрация, поиск
│       ├── DTO.java                      # Данные для регистрации
│       ├── UserRepository.java           # Запросы к базе по пользователям
│       ├── UserResponseDTO.java          # Ответ при регистрации
│       └── UserService.java              # Логика: регистрация, шифрование пароля
│
├── src/main/resources/
│   └── application.properties            # Настройки: порт, база данных
│
├── src/test/                             # Тесты
│   └── java/com/moneymymoney/
│       ├── transaction/                  # Тесты транзакций
│       │   ├── TransactionControllerTest.java
│       │   └── TransactionServiceTest.java
│       └── user/                         # Тесты пользователей
│           ├── UserControllerTest.java
│           └── UserServiceTest.java
│
├── pom.xml                               # Список библиотек (Spring, PostgreSQL)
├── .gitignore                            # Что не загружать в Git
└── README.md                             # Эта инструкция
├── Dockerfile                 # Описание сборки Docker-образа приложения
├── docker-compose.yml         # Конфигурация для запуска приложения и БД
├── .dockerignore              # Исключения для Docker-сборки


как запустить проект:
А.Запуск через Docker (рекомендуется)

Приложение полностью контейнеризировано с использованием Docker Compose.  
Для запуска достаточно выполнить следующие шаги:

1. Убедитесь, что на вашей системе установлены Docker и Docker Compose.
2. Клонируйте репозиторий и перейдите в корневую папку проекта.
3. Выполните команду в терминале:

   docker-compose up --build

4. После успешного запуска приложение будет доступно по адресу:
http://localhost:8080

Для остановки контейнеров нажмите Ctrl + C или выполните команду в терминале:

docker-compose down

При первом запуске автоматически создаются необходимые таблицы в базе данных PostgreSQL, а также 20+ предустановленных категорий транзакций.
Б. Запуск локально через Maven

1. Установи PostgreSQL
   Скачай тут: https://www.postgresql.org/download/
   Запомни пароль, который задашь при установке.
2. Создай базу данных
   Открой psql (SQL Shell) → введи:
   CREATE DATABASE moneymymoney;
3. Настрой файл application.properties
   Путь: src/main/resources/application.properties
   Вставь этот текст (замени password123 на свой пароль):
   properties
   server.port=8080
# Подключение к PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/moneymymoney
spring.datasource.username=postgres
spring.datasource.password=password123
# Hibernate - создаёт таблицы автоматически
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
4. Запусти проект
   В терминале в папке проекта:
# Перейди в папку проекта
cd C:\Users\Julia\IdeaProjects\MoneyMyMoney
# Собери проект
mvn clean install
# Запусти
mvn spring-boot:run
Готово! Приложение работает на http://localhost:8080
ЭНДПОИНТЫ:
1.Пользователи (UserController):
POST   /users/add                 - добавить пользователя
GET    /users/all                 - получить всех пользователей
GET    /users/{email}             - найти пользователя по email
POST   /users/register            - регистрация с созданием карты
2.Транзакции (TransactionController):
POST   /transactions/add                    - добавить транзакцию
GET    /transactions/balance/{cardId}       - баланс карты
GET    /transactions/history/{cardId}       - все транзакции по карте
GET    /transactions/stats/expense/{cardId} - расходы по категориям
GET    /transactions/stats/income/{cardId}  - доходы по категориям
Тестирование в Postman:
Шаг 1: Регистрация (создаёт пользователя и карту)

POST http://localhost:8080/users/register
Content-Type: application/json
json
{
"name": "Мария",
"email": "maria@mail.ru",
"login": "maria",
"password": "123",
"cardNumber": "1111222233334444",
"cardType": "VISA"
}
Сохрани из ответа: id пользователя (например, "id": 1)
Шаг 2: Получить всех пользователей

GET http://localhost:8080/users/all
Шаг 3: Добавить ДОХОД

POST http://localhost:8080/transactions/add
Content-Type: application/json
json
{
"sum": 50000.0,
"description": "Зарплата",
"type": "INCOME",
"cardId": 1,
"categoryId": 1
}
💡 ID категорий:
•	1 = Зарплата (доход)
•	2 = Доход (доход)
•	3 = Продукты (расход)
•	4 = Транспорт (расход)
•	и т.д. (всего 21 категория)
Шаг 4: Добавить РАСХОД

POST http://localhost:8080/transactions/add
Content-Type: application/json
json
{
"sum": 2500.0,
"description": "Продукты в магазине",
"type": "EXPENSE",
"cardId": 1,
"categoryId": 3
}
Шаг 5: Проверить баланс

GET http://localhost:8080/transactions/balance/1
Ответ: 47500.0 (50000 - 2500)
Шаг 6: История транзакций

GET http://localhost:8080/transactions/history/1
Шаг 7: Статистика расходов

GET http://localhost:8080/transactions/stats/expense/1
Ответ:
json
{
"Продукты": 2500.0
}
Шаг 8: Статистика доходов
text
GET http://localhost:8080/transactions/stats/income/1
Ответ:
json
{
"Зарплата": 50000.0
}
Если что-то не работает:
1. Ошибка подключения к PostgreSQL

PSQLException: Connection to localhost:5432 refused
Решение: убедитесь, что:
•	PostgreSQL запущен
•	База данных moneymymoney создана
•	Пользователь moneymymoney_user существует
•	Пароль в application.properties правильный
2. Ошибка при регистрации

500 Internal Server Error
Решение: Проверьте Body запроса — все поля должны быть заполнены:
•	name, email, login, password — обязательные
•	cardNumber и cardType — опциональные, но рекомендуемые
3. Категории не создаются
   Если при первом запуске категории не создались:
1.	Остановите приложение
2.	Удалите все таблицы в БД:
      sql
      DROP SCHEMA public CASCADE;
      CREATE SCHEMA public;
3.	Перезапустите приложение
4. Баланс не обновляется
   Если баланс карты показывает 0 после добавления транзакций:
   •	Убедитесь, что транзакция сохранилась (GET /transaction/card/{cardId})
   •	Проверьте тип транзакции: INCOME или EXPENSE
   •	Перезапустите приложение
   Запуск тестов:

# В папке проекта:
mvn test
Тестируются:
•	UserServiceTest - регистрация, поиск по email
•	TransactionServiceTest - добавление транзакций, баланс
•	UserControllerTest - API пользователей
•	TransactionControllerTest - API транзакций
Категории создаются автоматически:
При первом запуске создаются 21 категория:
•	2 категории доходов (Зарплата, Доход)
•	19 категорий расходов (Продукты, Транспорт, Развлечения, и т.д.)

Логика работы:
Добавление транзакции:
1.	Проверяется существование карты (cardId)
2.	Проверяется существование категории (categoryId)
3.	Создается транзакция с текущей датой
4.	Сохраняется транзакция в БД
5.	Пересчитывается баланс карты (вызов getCardBalance())
6.	Обновляется баланс карты в БД
      Расчет баланса:
      •	INCOME → balance = balance + sum
      •	EXPENSE → balance = balance - sum
      Статистика:
      •	getCategoryStatistics() — суммы по категориям расходов (EXPENSE)
      •	getCategoryStatisticsTwo() — суммы по категориям доходов (INCOME)


Планы развития проекта:
1g.Контроль расходов ребёнка / семейный бюджет:
Проблема: сейчас можно отслеживать только свои карты
Решение: добавить привязку нескольких карт к одному пользователю и семейные аккаунты.

// Новая сущность - Семейный аккаунт
@Entity
public class FamilyAccount {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    private String familyName;

    @OneToOne
    private User parent; // Родитель/основной пользователь

    @OneToMany
    private List<User> children; // Дети/дополнительные пользователи

    @OneToMany
    private List<Card> familyCards; // Все карты семьи
}
Что добавится:
•	API для создания семейного аккаунта
•	Привязка дополнительных карт (например, детской)
•	Лимиты расходов по категориям для детей
•	Уведомления родителю при превышении лимита
•	Отдельная статистика по каждому члену семьи
2.Планирование бюджета и цели накоплений
Проблема: только отслеживание, а не планирование.
Решение: система финансовых целей и планов.

// Цель накопления
@Entity
public class SavingGoal {
private String name; // "Ноутбук", "Отпуск"
private Double targetAmount; // 100000 руб.
private Double currentAmount; // 35000 руб.
private LocalDate targetDate; // 2024-12-31
private Boolean isActive;

    // Автоматическое перечисление при доходе
    private Double autoTransferPercent; // 10% от каждой зарплаты
}
Что добавится:
•	API для создания целей накопления
•	Автоматические отчисления с доходов
•	Визуализация прогресса
•	Советы по достижению целей
•	Совместные цели (для семьи)
3 Категоризация чеков по фото (OCR)
Проблема: вручную вводить каждую покупку утомительно
Решение: загрузка фото чека с автоматическим распознаванием.

@PostMapping("/transactions/receipt")
public Transaction addFromReceipt(@RequestParam("file") MultipartFile file) {
// 1. Загружаем фото чека
// 2. Распознаем текст (Tesseract OCR / Google Vision API)
// 3. Парсим данные: магазин, сумма, товары
// 4. Автоматически определяем категории
// 5. Создаем транзакции
Receipt receipt = ocrService.parseReceipt(file);

    // Разбиваем чек на несколько транзакций по товарам
    for (ReceiptItem item : receipt.getItems()) {
        String category = categoryService.autoDetect(item.getName());
        transactionService.addTransaction(
            item.getPrice(),
            item.getName(),
            "EXPENSE",
            receipt.getCardId(),
            category.getId()
        );
    }
}
Что добавится:
•	Загрузка фото чека через мобильное приложение
•	Распознавание QR-кодов
•	Разделение общего чека (например, в ресторане)
•	Архив чеков с поиском
Участие в разработке:
Проект открыт для всех, кто хочет помочь!
1.Исправление багов
2.Новые идеи и фичи
3.Улучшение документации
4.Дополнительные тесты
5.Перевод на другие языки
Контакты:
Разработчик: Юлия
GitHub:  @Julia56899
Email: gusak.julia233@gmail.com
Telegram: @julia8880
Вместе сделаем лучший финансовый трекер!
Последнее обновление: февраль, 2026 г.