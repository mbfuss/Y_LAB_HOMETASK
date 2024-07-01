package org.example;

import org.example.model.Booking;
import org.example.model.User;
import org.example.model.Resource;
import org.example.service.UserService;
import org.example.service.ResourceService;
import org.example.service.BookingService;
import org.example.utils.AuthUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class AppController {
    private UserService userService;
    private ResourceService resourceService;
    private BookingService bookingService;

    public AppController(UserService userService, ResourceService resourceService, BookingService bookingService) {
        this.userService = userService;
        this.resourceService = resourceService;
        this.bookingService = bookingService;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Регистрация");
            System.out.println("2. Аутентификация");
            System.out.println("3. Добавить ресурс (админ)");
            System.out.println("4. Бронирование ресурса");
            System.out.println("5. Просмотреть все ресурсы");
            System.out.println("6. Просмотреть все бронирования (админ)");
            System.out.println("7. Выйти");
            System.out.print("Выберите опцию: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    register(scanner);
                    break;
                case 2:
                    authenticate(scanner);
                    break;
                case 3:
                    addResource(scanner);
                    break;
                case 4:
                    bookResource(scanner);
                    break;
                case 5:
                    viewResources();
                    break;
                case 6:
                    viewBookings();
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        }
    }

    private void register(Scanner scanner) {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        System.out.print("Являетесь ли вы администратором? (да/нет): ");
        boolean isAdmin = scanner.nextLine().equalsIgnoreCase("да");

        try {
            userService.registerUser(username, password, isAdmin);
            System.out.println("Пользователь успешно зарегистрирован.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void authenticate(Scanner scanner) {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        try {
            User user = userService.authenticateUser(username, password);
            AuthUtils.login(user);
            System.out.println("Успешная аутентификация");
        } catch (IllegalArgumentException e) {
            System.out.println("Неверный логин или пароль");
        }
    }

    private void addResource(Scanner scanner) {
        if (!AuthUtils.isAdmin()) {
            System.out.println("Ошибка: Доступ запрещен");
            return;
        }

        System.out.print("Введите имя ресурса: ");
        String name = scanner.nextLine();
        System.out.print("Является ли ресурс конференц-залом? (да/нет): ");
        boolean isConferenceRoom = scanner.nextLine().equalsIgnoreCase("да");

        resourceService.addResource(name, isConferenceRoom);
        System.out.println("Ресурс успешно добавлен.");
    }

    private void bookResource(Scanner scanner) {
        if (AuthUtils.getLoggedInUser() == null) {
            System.out.println("Ошибка: Необходима аутентификация.");
            return;
        }

        System.out.print("Введите ID ресурса: ");
        Long resourceId = Long.parseLong(scanner.nextLine());
        Resource resource = resourceService.getResource(resourceId);
        if (resource == null) {
            System.out.println("Ошибка: Ресурс не найден.");
            return;
        }

        System.out.print("Введите дату и время начала (yyyy-MM-dd HH:mm): ");
        LocalDateTime startTime = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        System.out.print("Введите дату и время окончания (yyyy-MM-dd HH:mm): ");
        LocalDateTime endTime = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        bookingService.createBooking(AuthUtils.getLoggedInUser(), resource, startTime, endTime);
    }

    private void viewResources() {
        List<Resource> resources = resourceService.getAllResources();
        for (Resource resource : resources) {
            System.out.println(resource);
        }
    }

    private void viewBookings() {
        if (!AuthUtils.isAdmin()) {
            System.out.println("Ошибка: Доступ запрещен.");
            return;
        }

        List<Booking> bookings = bookingService.getAllBookings();
        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }
}
