package org.example;

import org.example.config.DatabaseConfig;
import org.example.repository.UserRepository;
import org.example.repository.ResourceRepository;
import org.example.repository.BookingRepository;
import org.example.service.UserService;
import org.example.service.ResourceService;
import org.example.service.BookingService;
import org.example.service.serviceFactory.ServiceFactory;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        try {
            DatabaseConfig databaseConfig = new DatabaseConfig();

            UserRepository userRepository = databaseConfig.createUserRepository();
            ResourceRepository resourceRepository = databaseConfig.createResourceRepository();
            BookingRepository bookingRepository = databaseConfig.createBookingRepository();

            ServiceFactory serviceFactory = new ServiceFactory(userRepository, resourceRepository, bookingRepository);

            UserService userService = serviceFactory.createUserService();
            ResourceService resourceService = serviceFactory.createResourceService();
            BookingService bookingService = serviceFactory.createBookingService();

            AppController appController = new AppController(userService, resourceService, bookingService);
            appController.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
