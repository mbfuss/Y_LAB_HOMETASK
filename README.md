# Y_LAB_HOMETASK_2


# Сервис управления рабочими местами

Этот проект представляет собой простой сервис управления рабочими местами, реализованный на Java с использованием Maven.

# Описание

Сервис позволяет пользователям просматривать доступные ресурсы, бронировать рабочие места и управлять своими бронированиями. Администраторы могут добавлять новые ресурсы в систему.

# Coworking Service

Это приложение для управления коворкинг-ресурсами, включая регистрацию пользователей, аутентификацию, бронирование ресурсов и управление бронированиями.

## Требования

- Java 11+
- PostgreSQL
- Liquibase

## Установка

1. Склонируйте репозиторий:
    ```sh
    git clone <repository-url>
    cd <repository-directory>
    ```

2. Настройте PostgreSQL базу данных и измените параметры в файле `config.properties`:
    ```properties
    db.url=jdbc:postgresql://localhost:5432/coworking
    db.username=yourusername
    db.password=yourpassword
    ```

3. Запустите миграции Liquibase для создания и заполнения таблиц:
    ```sh
    ./liquibase --changeLogFile=db/changelog/db.changelog-master.yaml update
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

## Docker

Для запуска PostgreSQL в контейнере Docker используйте `docker-compose.yml`:

```yaml
version: '3.8'
services:
  db:
    image: postgres:latest
    environment:
      POSTGRES_DB: coworking
      POSTGRES_USER: usver
      POSTGRES_PASSWORD: 12345
    ports:
      - "5432:5432"

```

# Вклад в проект

Вы можете внести свой вклад в проект, создавая pull request'ы с предложениями улучшений или исправлений.



 
