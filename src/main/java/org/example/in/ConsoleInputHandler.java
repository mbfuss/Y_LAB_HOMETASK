package org.example.in;

import java.util.Scanner;

public class ConsoleInputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getInput() {
        return scanner.nextLine();
    }
}
