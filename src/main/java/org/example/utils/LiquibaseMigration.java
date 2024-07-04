package org.example.utils;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Класс для выполнения миграций базы данных с использованием Liquibase.
 */
public class LiquibaseMigration {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            // Загружаем настройки из файла config.properties
            properties.load(new FileInputStream("config.properties"));

            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");
            String changelog = properties.getProperty("db.changelog");

            // Подключаемся к базе данных
            Connection connection = DriverManager.getConnection(url, username, password);

            // Настраиваем Liquibase для выполнения миграций
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(changelog, new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts());

            // Закрываем соединение
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}