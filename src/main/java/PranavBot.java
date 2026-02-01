import java.util.Scanner;
import java.util.ArrayList;

/**
 * The main entry point for PranavBot, a simple task management chatbot.
 */
public class PranavBot {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> tasks = new ArrayList<>();  // Stores all added tasks in a dynamic array

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

            if (input.equalsIgnoreCase("list")) {
                if (tasks.isEmpty()) {
                    System.out.println("Your task list is empty.");
                } else {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                }
                continue;  // Skip adding "list" as a task
            }

            // Add the input as a task (if not a special command)
            if (!input.isEmpty()) {
                tasks.add(input);
                System.out.println("added: " + input);
            }

        }

        scanner.close();
    }
}