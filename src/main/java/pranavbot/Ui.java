package pranavbot;

import java.util.Scanner;

/**
 * Handles all user interaction, including displaying messages and reading input.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Handles all user interaction, including displaying messages and reading input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message to the user.
     */
    public void showWelcome() {
        System.out.println("Hello! I'm pranavbot.PranavBot.");
        System.out.println("What can I do for you?");
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Reads a line of command from the user.
     *
     * @return the input line, or null if end of input stream is reached
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays an error message to the user.
     *
     * @param message the error message to display
     */
    public void showError(String message) {
        System.out.println("OOPSIE!!! " + message);
    }

    /**
     * Displays the goodbye message when exiting.
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

}
