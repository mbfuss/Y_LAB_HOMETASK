package org.example;

import org.example.in.ConsoleInputHandler;
import org.example.model.Resource;
import org.example.model.User;
import org.example.out.ConsoleOutputHandler;
import org.example.service.BookingService;
import org.example.service.ResourceService;
import org.example.service.UserService;
import org.example.utils.AuthUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

/**
 * Главный класс приложения для управления бронированиями ресурсов.
 */
public class App {
    private static UserService userService = new UserService();
    private static ResourceService resourceService = new ResourceService();
    private static BookingService bookingService = new BookingService();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Точка входа в приложение.
     *
     */
    public static void main(String[] args) {
        ConsoleOutputHandler.print("Welcome to Coworking Service!");

        while (true) {
            if (AuthUtils.getLoggedInUser() == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    /**
     * Отображает меню входа и регистрации пользователя.
     */
    private static void showLoginMenu() {
        ConsoleOutputHandler.print("1. Register");
        ConsoleOutputHandler.print("2. Login");
        ConsoleOutputHandler.print("3. Exit");
        ConsoleOutputHandler.print("Choose an option: ");

        String choice = ConsoleInputHandler.getInput();

        switch (choice) {
            case "1":
                registerUser();
                break;
            case "2":
                loginUser();
                break;
            case "3":
                System.exit(0);
                break;
            default:
                ConsoleOutputHandler.print("Invalid choice. Please try again.");
        }
    }

    /**
     * Регистрирует нового пользователя.
     */
    private static void registerUser() {
        ConsoleOutputHandler.print("Enter username: ");
        String username = ConsoleInputHandler.getInput();

        ConsoleOutputHandler.print("Enter password: ");
        String password = ConsoleInputHandler.getInput();

        ConsoleOutputHandler.print("Are you an admin? (yes/no): ");
        boolean isAdmin = ConsoleInputHandler.getInput().equalsIgnoreCase("yes");

        try {
            userService.registerUser(username, password, isAdmin);
            ConsoleOutputHandler.print("User registered successfully!");
        } catch (IllegalArgumentException e) {
            ConsoleOutputHandler.print(e.getMessage());
        }
    }

    /**
     * Вход пользователя в систему.
     */
    private static void loginUser() {
        ConsoleOutputHandler.print("Enter username: ");
        String username = ConsoleInputHandler.getInput();

        ConsoleOutputHandler.print("Enter password: ");
        String password = ConsoleInputHandler.getInput();

        try {
            User user = userService.authenticateUser(username, password);
            AuthUtils.login(user);
            ConsoleOutputHandler.print("Login successful!");
        } catch (IllegalArgumentException e) {
            ConsoleOutputHandler.print(e.getMessage());
        }
    }

    /**
     * Основное меню приложения после входа в систему.
     */
    private static void showMainMenu() {
        ConsoleOutputHandler.print("1. View available resources");
        ConsoleOutputHandler.print("2. View available slots for a resource");
        ConsoleOutputHandler.print("3. Book a resource");
        ConsoleOutputHandler.print("4. Cancel a booking");
        ConsoleOutputHandler.print("5. View all bookings");
        ConsoleOutputHandler.print("6. Add new resource (Admin)");
        ConsoleOutputHandler.print("7. Logout");
        ConsoleOutputHandler.print("Choose an option: ");

        String choice = ConsoleInputHandler.getInput();

        switch (choice) {
            case "1":
                viewResources();
                break;
            case "2":
                viewAvailableSlots();
                break;
            case "3":
                bookResource();
                break;
            case "4":
                cancelBooking();
                break;
            case "5":
                viewAllBookings();
                break;
            case "6":
                if (AuthUtils.isAdmin()) {
                    addResource();
                } else {
                    ConsoleOutputHandler.print("Access denied. Admins only.");
                }
                break;
            case "7":
                AuthUtils.logout();
                ConsoleOutputHandler.print("Logged out successfully.");
                break;
            default:
                ConsoleOutputHandler.print("Invalid choice. Please try again.");
        }
    }

    /**
     * Просмотр доступных ресурсов.
     */
    private static void viewResources() {
        Map<String, Resource> resources = resourceService.getAllResources();
        if (resources.isEmpty()) {
            ConsoleOutputHandler.print("No resources available.");
        } else {
            resources.forEach((id, resource) -> ConsoleOutputHandler.print(
                    "ID: " + id + ", Name: " + resource.getName() + ", Is Conference Room: " + resource.isConferenceRoom()));
        }
    }

    /**
     * Просмотр доступных временных слотов для указанного ресурса.
     */
    private static void viewAvailableSlots() {
        ConsoleOutputHandler.print("Enter resource ID: ");
        String resourceId = ConsoleInputHandler.getInput();
        Resource resource = resourceService.getResource(resourceId);

        if (resource != null) {
            ConsoleOutputHandler.print("Enter date (yyyy-MM-dd): ");
            String date = ConsoleInputHandler.getInput();

            try {
                LocalDateTime startDateTime = LocalDateTime.parse(date + " 00:00", formatter);
                LocalDateTime endDateTime = LocalDateTime.parse(date + " 23:59", formatter);

                bookingService.getBookingsByResource(resource).stream()
                        .filter(booking -> booking.getStartTime().isAfter(startDateTime) && booking.getEndTime().isBefore(endDateTime))
                        .forEach(booking -> ConsoleOutputHandler.print(
                                "Booking ID: " + booking.getId() +
                                        ", Start Time: " + booking.getStartTime().format(formatter) +
                                        ", End Time: " + booking.getEndTime().format(formatter)));
            } catch (DateTimeParseException e) {
                ConsoleOutputHandler.print("Invalid date format. Please enter date in yyyy-MM-dd format.");
            }
        } else {
            ConsoleOutputHandler.print("Resource not found.");
        }
    }

    /**
     * Бронирование ресурса.
     */
    private static void bookResource() {
        ConsoleOutputHandler.print("Enter resource ID: ");
        String resourceId = ConsoleInputHandler.getInput();
        Resource resource = resourceService.getResource(resourceId);

        if (resource != null) {
            ConsoleOutputHandler.print("Enter start time (yyyy-MM-dd HH:mm): ");
            String startTimeInput = ConsoleInputHandler.getInput();

            ConsoleOutputHandler.print("Enter end time (yyyy-MM-dd HH:mm): ");
            String endTimeInput = ConsoleInputHandler.getInput();

            try {
                LocalDateTime startTime = LocalDateTime.parse(startTimeInput, formatter);
                LocalDateTime endTime = LocalDateTime.parse(endTimeInput, formatter);

                try {
                    String bookingId = String.valueOf(System.currentTimeMillis());
                    bookingService.createBooking(bookingId, AuthUtils.getLoggedInUser(), resource, startTime, endTime);
                    ConsoleOutputHandler.print("Booking created successfully!");
                } catch (IllegalArgumentException e) {
                    ConsoleOutputHandler.print(e.getMessage());
                }
            } catch (DateTimeParseException e) {
                ConsoleOutputHandler.print("Invalid date/time format. Please enter date/time in yyyy-MM-dd HH:mm format.");
            }
        } else {
            ConsoleOutputHandler.print("Resource not found.");
        }
    }

    /**
     * Отмена бронирования.
     */
    private static void cancelBooking() {
        ConsoleOutputHandler.print("Enter booking ID: ");
        String bookingId = ConsoleInputHandler.getInput();

        try {
            bookingService.cancelBooking(bookingId);
            ConsoleOutputHandler.print("Booking canceled successfully!");
        } catch (IllegalArgumentException e) {
            ConsoleOutputHandler.print(e.getMessage());
        }
    }

    /**
     * Просмотр всех бронирований.
     */
    private static void viewAllBookings() {
        if (bookingService.getBookings().isEmpty()) {
            ConsoleOutputHandler.print("No bookings found.");
        } else {
            bookingService.getBookings().forEach(booking -> ConsoleOutputHandler.print(
                    "Booking ID: " + booking.getId() +
                            ", User: " + booking.getUser().getUsername() +
                            ", Resource: " + booking.getResource().getName() +
                            ", Start Time: " + booking.getStartTime().format(formatter) +
                            ", End Time: " + booking.getEndTime().format(formatter)));
        }
    }

    /**
     * Добавление нового ресурса (только для администраторов).
     */
    private static void addResource() {
        ConsoleOutputHandler.print("Enter resource ID: ");
        String id = ConsoleInputHandler.getInput();

        ConsoleOutputHandler.print("Enter resource name: ");
        String name = ConsoleInputHandler.getInput();

        ConsoleOutputHandler.print("Is it a conference room? (yes/no): ");
        boolean isConferenceRoom = ConsoleInputHandler.getInput().equalsIgnoreCase("yes");

        try {
            resourceService.addResource(id, name, isConferenceRoom);
            ConsoleOutputHandler.print("Resource added successfully!");
        } catch (IllegalArgumentException e) {
            ConsoleOutputHandler.print(e.getMessage());
        }
    }
}
