package org.example.utils;

import org.example.model.User;

public class AuthUtils {
    private static User loggedInUser;

    public static void login(User user) {
        loggedInUser = user;
    }

    public static void logout() {
        loggedInUser = null;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static boolean isAdmin() {
        return loggedInUser != null && loggedInUser.isAdmin();
    }
}
