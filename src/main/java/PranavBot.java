import java.util.Scanner;

/**
 * The main entry point for PranavBot, a simple task management chatbot.
 */
public class PranavBot {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello! I'm PranavBot.");
        System.out.println("What can I do for you?");

        // Main interaction loop
        while (true) {
            String input = scanner.nextLine();  // Read full line, preserve spaces

            // Echo the user's input
            System.out.println("You said: " + input);

            // Check for exit command (case-insensitive)
            if (input.trim().equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

        }

        scanner.close();
    }
}