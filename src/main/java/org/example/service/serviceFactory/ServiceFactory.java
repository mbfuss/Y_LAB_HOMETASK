package org.example.service.serviceFactory;

import org.example.repository.BookingRepository;
import org.example.repository.ResourceRepository;
import org.example.repository.UserRepository;
import org.example.service.BookingService;
import org.example.service.ResourceService;
import org.example.service.UserService;
import org.example.service.impl.BookingServiceImpl;
import org.example.service.impl.ResourceServiceImpl;
import org.example.service.impl.UserServiceImpl;

public class ServiceFactory {
    private final UserRepository userRepository;
    private final ResourceRepository resourceRepository;
    private final BookingRepository bookingRepository;

    public ServiceFactory(UserRepository userRepository, ResourceRepository resourceRepository, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.resourceRepository = resourceRepository;
        this.bookingRepository = bookingRepository;
    }

    public UserService createUserService() {
        return new UserServiceImpl(userRepository);
    }

    public ResourceService createResourceService() {
        return new ResourceServiceImpl(resourceRepository);
    }

    public BookingService createBookingService() {
        return new BookingServiceImpl(bookingRepository);
    }
}
