# RESTful API сервис для отслеживания местоположения животного

## Обзор

Приложение реализует ТЗ по созданию RESTful API сервис для регистрации животных и их текущего местоположения. ТЗ взято с первого этапа конкурса "Прикладное программирование if...else"

## Функциональность
- Аутентификация пользователя в системе 
- Добавление, изменение, удаление информации о пользователях, животных, типов животных, локаций
- Валидация параметров запросов

## Запуск
Для запуска используется docker-compose файл с прохождением автотестов. Используйте команду `docker-compose up` в терминале для поднятия контейнеров.
Результаты тестов лежат по адресу `http://localhost:8090`

## Стек технологий
- Java 17
- Spring Boot 3
- Gradle
- Lombock

## ТЗ
Полное техническое задание доступно по ссылке: https://docs.google.com/document/d/1cUVHZfEo4uMGJBBADbRMQzdTjSsySiFJ3xVvZ8DsQ7o/edit
