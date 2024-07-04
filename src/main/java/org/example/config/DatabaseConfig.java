package org.example.config;

import liquibase.integration.commandline.Main;
import org.example.repository.UserRepository;
import org.example.repository.ResourceRepository;
import org.example.repository.BookingRepository;

import java.io.IOException;
import java.util.Properties;

/**
 * Класс для конфигурации базы данных и создания репозиториев.
 */
public class DatabaseConfig {
    private Properties properties;

    /**
     * Конструктор для инициализации настроек базы данных из файла config.properties.
     * throws IOException если произошла ошибка при загрузке файла настроек.
     */
    public DatabaseConfig() throws IOException {
        properties = new Properties();
        properties.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));
    }

    /**
     * Метод для создания репозитория пользователей.
     *  UserRepository объект репозитория пользователей.
     */
    public UserRepository createUserRepository() {
        return new UserRepository(getUrl(), getUsername(), getPassword());
    }

    /**
     * Метод для создания репозитория ресурсов.
     * ResourceRepository объект репозитория ресурсов.
     */
    public ResourceRepository createResourceRepository() {
        return new ResourceRepository(getUrl(), getUsername(), getPassword());
    }

    /**
     * Метод для создания репозитория бронирований.
     * BookingRepository объект репозитория бронирований.
     */
    public BookingRepository createBookingRepository() {
        return new BookingRepository(getUrl(), getUsername(), getPassword());
    }

    /**
     * Метод для получения URL базы данных из файла настроек.
     * строка URL базы данных.
     */
    private String getUrl() {
        return properties.getProperty("db.url");
    }

    /**
     * Метод для получения имени пользователя базы данных из файла настроек.
     * строка имени пользователя базы данных.
     */
    private String getUsername() {
        return properties.getProperty("db.username");
    }

    /**
     * Метод для получения пароля базы данных из файла настроек.
     * строка пароля базы данных.
     */
    private String getPassword() {
        return properties.getProperty("db.password");
    }
}
