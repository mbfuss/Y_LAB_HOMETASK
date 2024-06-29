package org.example;

import liquibase.integration.commandline.Main;
import org.example.repository.UserRepository;
import org.example.repository.ResourceRepository;
import org.example.repository.BookingRepository;
import org.example.service.UserService;
import org.example.service.ResourceService;
import org.example.service.BookingService;

import java.io.IOException;
import java.util.Properties;

public class App {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));

            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            UserRepository userRepository = new UserRepository(url, username, password);
            ResourceRepository resourceRepository = new ResourceRepository(url, username, password);
            BookingRepository bookingRepository = new BookingRepository(url, username, password);

            UserService userService = new UserService(userRepository);
            ResourceService resourceService = new ResourceService(resourceRepository);
            BookingService bookingService = new BookingService(bookingRepository);

            AppController appController = new AppController(userService, resourceService, bookingService);
            appController.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
