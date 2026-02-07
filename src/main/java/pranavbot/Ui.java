package pranavbot;

import java.util.Scanner;

/**
 * Handles all user interaction (input and output).
 */
public class Ui {
    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("Hello! I'm PranavBot.");
        System.out.println("What can I do for you?");
    }

    /**
     * Reads a line of input from the user.
     * Returns null if the input stream has ended (e.g., when running via Gradle run with no piped input).
     */
    public String readCommand() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return null;  // End of input
    }

    public void showError(String message) {
        System.out.println("OOPSIE!!! " + message);
    }

    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }
}