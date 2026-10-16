package ui;

import dao.UserDao;
import model.User;

import java.util.Optional;
import java.util.Scanner;

/**
 * The first screen: Register / Login / Exit.
 */
public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final UserDao userDao = new UserDao();

    public void show() {
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("=================================");
            System.out.println("  PeerPal - Peer Tutoring System");
            System.out.println("=================================");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> register();
                case "2" -> login();
                case "0" -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void register() {
        System.out.println("\n--- Register Account ---");

        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        if (username.isEmpty() || username.contains(" ")) {
            System.out.println("Username cannot be empty or contain spaces.");
            return;
        }
        if (userDao.usernameExists(username)) {
            System.out.println("That username is already taken.");
            return;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();
        if (password.isBlank()) {
            System.out.println("Password cannot be empty.");
            return;
        }

        System.out.println("Select role:");
        System.out.println("1. TEACHER");
        System.out.println("2. STUDENT");
        System.out.print("Choose: ");
        String role;
        switch (scanner.nextLine().trim()) {
            case "1" -> role = "TEACHER";
            case "2" -> role = "STUDENT";
            default -> {
                System.out.println("Invalid role. Registration cancelled.");
                return;
            }
        }

        if (userDao.register(username, password, role)) {
            System.out.println("Account created! Role: " + role + " | Status: PENDING");
        } else {
            System.out.println("Registration failed. Username may already exist.");
        }
    }

    private void login() {
        System.out.println("\n--- Login ---");

        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Optional<User> result = userDao.login(username, password);

        if (result.isPresent()) {
            new DashboardMenu(result.get(), scanner).show();
        } else {
            System.out.println("Invalid username or password.");
        }
    }
}
