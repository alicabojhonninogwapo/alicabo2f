package ui;

import model.User;

import java.util.Scanner;

/**
 * Screen shown after a successful login. Logging out returns to the main menu.
 */
public class DashboardMenu {

    private final User user;
    private final Scanner scanner;

    public DashboardMenu(User user, Scanner scanner) {
        this.user = user;
        this.scanner = scanner;
    }

    public void show() {
        System.out.println("\nLogin successful! Welcome, " + user.username() + ".");

        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println();
            System.out.println("--- Dashboard ---");
            System.out.println("Username : " + user.username());
            System.out.println("Role     : " + user.roles());
            System.out.println("Status   : " + user.status());
            if ("PENDING".equals(user.status())) {
                System.out.println("(Your account is still waiting for approval.)");
            }
            System.out.println("1. Logout");
            System.out.print("Choose: ");

            if (scanner.nextLine().trim().equals("1")) {
                System.out.println("You have been logged out.");
                loggedIn = false;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
