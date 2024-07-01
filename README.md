# Y_LAB_HOMETASK_2

# Описание

Сервис позволяет пользователям просматривать доступные ресурсы, бронировать рабочие места и управлять своими бронированиями. Администраторы могут добавлять новые ресурсы в систему.

## Требования

- Java 11+
- PostgreSQL
- Liquibase
- Docker

## Установка

1. Склонируйте репозиторий

2. Docker

Для запуска PostgreSQL в контейнере Docker используйте `docker-compose.yml`:

```yaml
version: '3.8'
services:
  db:
    image: postgres:latest
    environment:
      POSTGRES_DB: coworking
      POSTGRES_USER: coworking
      POSTGRES_PASSWORD: coworking
    ports:
      - "5432:5432"

```

3. Настройте PostgreSQL базу данных и измените параметры в файле `config.properties`:

    ```properties
    db.url=jdbc:postgresql://localhost:5432/coworking
    db.username=coworking
    db.password=coworking
    ```

4. Запустите миграции Liquibase для создания и заполнения таблиц:

    ```sh
    mvn liquibase:update
    ```

## Запуск

1. Соберите проект:
    ```sh
    mvc clean package
    ```

2. Запустите приложение:
    ```sh
    java -jar build/libs/coworking-service-1.0-SNAPSHOT.jar
    ```

2.1. ИЛИ откройте проект через IDE, после чего запустите App.java 



# Вклад в проект

Вы можете внести свой вклад в проект, создавая pull request'ы с предложениями улучшений или исправлений.
