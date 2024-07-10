# Y_LAB_HOMETASK_3

# Функциональные возможности

**1. Регистрация**

**2. Аутентификация**

**3. Добавление ресурса**

**4. Бронирование ресурса**

**5. Просмотр всех ресурсов**

**6. Просмотр всех бронирований**

**7. Удаление бронирования**

**8. Выход из системы**

# Требования

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

3. Настройте PostgreSQL базу данных и измените параметры в файлах `config.properties` и `liquibase.properties`:

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

2. Разверните приложение на сервере приложений

# Вклад в проект

Вы можете внести свой вклад в проект, создавая pull request'ы с предложениями улучшений или исправлений.
