import java.util.ArrayList;
import java.util.Scanner;

/**
 * The main entry point for PranavBot, a simple task management chatbot.
 */
public class PranavBot {

    /**
     * Represents a single task with description and completion status. For now it will be included
     * within this file.
     */
    private static class Task {
        private final String description;
        private boolean isDone = false;

        public Task(String description) {
            this.description = description;
        }

        public void markAsDone() {
            this.isDone = true;
        }

        public void markAsNotDone() {
            this.isDone = false;
        }

        private String getStatusIcon() {
            return isDone ? "[X]" : "[ ]";
        }

        @Override
        public String toString() {
            return getStatusIcon() + " " + description;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();  // Stores all tasks with status in a dynamic array

        // Greeting
        System.out.println("Hello! I'm PranavBot.");
        System.out.println("What can I do for you?");

        while (true) {
            String input = scanner.nextLine().trim();

            // Echo the input exactly
            System.out.println("You said: " + input);

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            if (input.equalsIgnoreCase("list")) {
                if (tasks.isEmpty()) {
                    System.out.println("Your task list is empty.");
                } else {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                }
                continue;  // So it won't be added to list
            }

            // Handle mark command
            if (input.toLowerCase().startsWith("mark ")) {
                try {
                    int index = Integer.parseInt(input.substring(5).trim()) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println(tasks.get(index));
                    } else {
                        System.out.println("OOPSIE!!! Invalid task number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("OOPSIE!!! Please provide a valid number after 'mark'.");
                }
                continue;
            }

            // Handle unmark command
            if (input.toLowerCase().startsWith("unmark ")) {
                try {
                    int index = Integer.parseInt(input.substring(7).trim()) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsNotDone();
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println(tasks.get(index));
                    } else {
                        System.out.println("OOPS!!! Invalid task number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("OOPS!!! Please provide a valid number after 'unmark'.");
                }
                continue;
            }

            // Add new task
            if (!input.isEmpty()) {
                tasks.add(new Task(input));
                System.out.println("added: " + input);
            }
        }

        scanner.close();
    }
}