MoneyMyMoney — это современное веб-приложение для управления личными финансами, разработанное на Spring Boot.
Приложение помогает контролировать доходы и расходы, анализировать финансовые привычки и оптимизировать бюджет.

Ключевые особенности:
1.Умный учет операций — добавление доходов и расходов с автоматическим расчетом баланса
2.Категорийный анализ — группировка операций по категориям для понятной статистики
3.Мультикарточность — управление несколькими банковскими картами с отдельными балансами
4.Безопасное хранение — шифрование паролей и защита персональных данных
5.Простой запуск — готовые Docker-контейнеры для быстрого развертывания

Быстрый запуск приложения
Вариант 1: Самый простой способ (рекомендуется).
1. Откройте терминал на вашем компьютере
- Windows:PowerShell или Командная строка
-Mac/Linux:Terminal
2. Введите одну команду:docker run -d -p 8090:8080 --name money-app moneymymoney
3. Откройте в браузере:http://localhost:8090
Готово!Приложение запущено и готово к работе. 

4. Вариант 2: Если хотите собрать самостоятельно.
Шаг 1: Установите Docker
1. Скачайте Docker Desktop с официального сайта: https://www.docker.com/products/docker-desktop
2. Установите программу (просто нажимайте "Далее")
3. Запустите Docker Desktop
 Шаг 2: Подготовьте проект
1. Скопируйте проект к себе на компьютер:
git clone https://github.com/julia56899/MoneyMyMoney.git
cd MoneyMyMoney
2. Соберите Docker-образ (это как "упаковка" приложения):
docker build -t moneymymoney .
 Шаг 3: Запустите приложение
docker run -d -p 8090:8080 --name money-app moneymymoney
Шаг 4: Проверьте что всё работает
# Посмотрите запущенные контейнеры
docker ps
# Откройте в браузере
http://localhost:8090

Если что-то пошло не так:
1.Проблема: Порт 8090 уже занят.
# Используйте другой порт, например 8091
docker run -d -p 8091:8080 --name money-app moneymymoney
# Затем откройте http://localhost:8091
2.Проблема: Ошибка "image not found"
# Сначала соберите образ
docker build -t moneymymoney .
# Затем запустите
docker run -d -p 8090:8080 --name money-app moneymymoney
3.Проблема: Контейнер с таким именем уже существует
# Удалите старый контейнер
docker stop money-app && docker rm money-app
# Запустите новый
docker run -d -p 8090:8080 --name money-app moneymymoney

Быстрая команда для запуска (скопируйте и вставьте):
git clone https://github.com/julia56899/MoneyMyMoney.git && cd MoneyMyMoney && docker build -t moneymymoney . && docker run -d -p 8090:8080 --name money-app moneymymoney
После этого откройте http://localhost:8090 и начните управлять своими финансами! 

Начало работы с приложением:
Шаг 1: Открой Postman
1. Создай новый запрос
2. Выбери метод POST
3. Введи URL: `http://localhost:8090/api/users/register`
Шаг 2: Настрой Headers
- Нажми на вкладку Headers
- Добавь: `Content-Type` = `application/json`

Шаг 3: Введи данные
- Перейди на вкладку Body
- Выбери raw** и JSON
- введи JSON
  {
  "name": "Юлия Петрова",
  "email": "julia@example.com",
  "login": "julia56899",
  "password": "myPassword123",
  "cardNumber": "1234567890123456",
  "cardType": "VISA"
  }
- Нажми Send

Шаг 4: Смотри ответ
- В нижней части Postman увидишь ответ сервера
  {
  "id": 1,
  "name": "Юлия Петрова",
  "email": "julia@example.com",
  "login": "julia56899",
  "password": "$2a$10$N9qo8uLOickgx2ZMRZoMye...", // зашифрованный пароль
  "createdAt": "2024-12-04T16:52:30"
  }
- Сохрани `cardId` из ответа

Шаг 5: Добавь транзакции
- Создай новый запрос
- URL: `http://localhost:8090/transactions/add`
- Используй тот же формат.
введи боди: 
{
  "sum": 50000.00,
  "description": "Зарплата за декабрь",
  "type": "INCOME",
  "cardId": 1,
  "categoryId": 1
  }
ответ ниже:
  {
  "id": 1,
  "sum": 50000.00,
  "description": "Зарплата за декабрь",
  "type": "INCOME",
  "cardId": 1,
  "categoryId": 1,
  "date": "2024-12-04",
  "cardBalance": 50000.00
  }
дальше протестирую транзакцию расхода и посмотри на баланс: 
введи в боди: 
{
  "sum": 2500.00,
  "description": "Покупка продуктов в Ашане",
  "type": "EXPENSE",
  "cardId": 1,
  "categoryId": 3
  }
ответ ниже получишь:
  {
  "id": 2,
  "sum": 2500.00,
  "description": "Покупка продуктов в Ашане",
  "type": "EXPENSE",
  "cardId": 1,
  "categoryId": 3,
  "date": "2024-12-04",
  "cardBalance": 47500.00
  }
ЧТО ТЕБЕ НУЖНО ЗНАТЬ:
1. Где брать cardId?
После регистрации карта создается автоматически. cardId = 1(первая карта пользователя)
2. Где брать categoryId?
Нужно сначала получить список категорий:
GET http://localhost:8090/api/categories
Ответ покажет все категории с их ID.
3. Как проверить что всё работает?
# Проверить баланс карты
GET http://localhost:8090/transactions/balance/1
Ответ: 47500.00
#Посмотреть историю операций
GET http://localhost:8090/transactions/history/1

Структура проекта:
MoneyMyMoney/
├── src/main/java/com/moneymymoney/
│   ├── user/          # Пользователи (User, UserController, UserService, UserRepository)
│   ├── transaction/   # Транзакции (TransactionController, TransactionService, TransactionRepository, TransactionDTO)
│   ├── card/          # Банковские карты (CardRepository)
│   └── category/      # Категории операций (ExpenseCategory, CategoryExpenseRepository)
├── src/main/resources/
│   └── application.properties  # Конфигурация приложения
├── Dockerfile         # Конфигурация для контейнеризации
├── Dockerfile.save    # Резервная конфигурация Docker
├── pom.xml            # Зависимости Maven
└── MoneyMyMoneyApplication.java  # Главный класс приложения
Требования для запуска
Необходимое программное обеспечение:
Минимальные требования:
- Docker 20.10+(рекомендуемый способ)
- Git для клонирования репозитория
- Postman или curl для тестирования API
Альтернативно (для разработки):
- Java 17 или выше
- Maven 3.6+ для сборки
- PostgreSQL 13+ для локальной базы данных
- IntelliJ IDEA или другая Java IDE
Системные требования:
- Оперативная память: 2 GB минимум (4 GB рекомендуется)
- Свободное место:500 MB
- ОС: Windows 10+, macOS 10.15+, Linux (Ubuntu 20.04+)
Что демонстрирует этот проект:
Технические навыки:
-Backend разработка - Java 17, Spring Boot 3, REST API  
-Работа с базами данных - PostgreSQL, Spring Data JPA, Hibernate  
-Контейнеризация - Docker, создание и управление контейнерами  
-Архитектура - MVC, слоистая архитектура, DTO паттерн  
-Безопасность - BCrypt шифрование паролей, защита данных

- Процесс разработки:
1.Полный цикл - от идеи до работающего приложения  
2.Деплой- упаковка в Docker, запуск на любом окружении  
3.Документация - подробный README, инструкции по запуску  
4.Отладка- работа с логами, решение проблем подключения
 
Долгосрочное развитие:
1.Инвестиционный трекер  - учет акций и облигаций
2.Прогнозирование расходов  на основе истории
3.Мультивалютность - поддержка разных валют
4.API для разработчиков - возможность интеграции

 Контакты разработчика:
- Email:gusak.julia233@gmail.com
- GitHub:https://github.com/julia56899
- Проект на GitHub:https://github.com/julia56899/MoneyMyMoney
 Обратная связь:
Приветствуются любые вопросы, предложения по улучшению или сообщения об ошибках!
Как связаться:
1.Создать Issue в репозитории GitHub
2. Написать на email с темой "MoneyMyMoney Feedback"
3. Предложить улучшение  через Pull Request
Что можно обсудить:
- Идеи по улучшению функциональности
- Найденные баги или проблемы
- Предложения по оптимизации кода
- Вопросы по архитектуре или реализации

MoneyMyMoney — готовое решение для управления финансами
Этот проект демонстрирует профессиональный подход к разработке:
- От чистого кода и архитектуры
- До готового к production приложения
- С полной документацией и инструкциями

Проект обновлен: Декабрь 2025 г.  
Технологии: Java 17 • Spring Boot 3 • PostgreSQL • Docker • Maven*
