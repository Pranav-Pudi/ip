import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The main entry point for PranavBot, a simple task management chatbot.
 */
public class PranavBot {
    private static final TaskList tasks = new TaskList();
    private static final Storage storage = new Storage("data/tasks.txt");

    static {
        // Load existing tasks on startup
        List<Task> loaded = storage.load();
        tasks.addAll(loaded);
        System.out.println("Loaded " + loaded.size() + " tasks from file.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello! I'm PranavBot.");
        System.out.println("What can I do for you?");

        while (true) {
            String input = scanner.nextLine().trim();

            // Handle empty input
            if (input.isEmpty()) {
                System.out.println("Please enter a command.");
                continue;
            }

            // Echo the input exactly
            System.out.println("You said: " + input);

            /**
             * The regex \\s+ matches all consecutive whitespace characters [essentialy a whitespace "block"]
             * The limit of 2 [second argument to our function] splits the string into 2 parts
             */
            String[] parts = input.split("\\s+", 2);
            String command = parts[0].toLowerCase();  // We take the first part

            try {
                switch (command) {
                    case "bye":
                        storage.save(tasks.getAll()); // Final save on exit
                        System.out.println("Bye. Hope to see you again soon!");
                        scanner.close();
                        return;

                    case "list":
                        if (tasks.isEmpty()) {
                            System.out.println("Your task list is empty.");
                        } else {
                            System.out.println("Here are the tasks in your list:");
                            for (int i = 0; i < tasks.size(); i++) {
                                System.out.println((i + 1) + "." + tasks.get(i));
                            }
                        }
                        break;

                    case "mark":
                        /** If the length is less than 2 it implies the command was "mark" + optional whitespaces
                         * We then check the index and handle errors appropriately in the if and exception blocks below
                         * The unmark code works similarly
                         */
                        if (parts.length < 2) {
                            System.out.println("OOPSIE!!! Please specify a task number.");
                            break;
                        }
                        int markIndex = Integer.parseInt(parts[1]) - 1;
                        if (markIndex < 0 || markIndex >= tasks.size()) {
                            System.out.println("OOPSIE!!! Invalid task number.");
                        } else {
                            tasks.mark(markIndex);
                            System.out.println("Nice!! I've marked this task as done:");
                            System.out.println(tasks.get(markIndex));
                            storage.save(tasks.getAll());
                        }
                        break;

                    case "unmark":
                        if (parts.length < 2) {
                            System.out.println("OOPSIE!!! Please specify a task number.");
                            break;
                        }
                        int unmarkIndex = Integer.parseInt(parts[1]) - 1;
                        if (unmarkIndex < 0 || unmarkIndex >= tasks.size()) {
                            System.out.println("OOPSIE!!! Invalid task number.");
                        } else {
                            tasks.unmark(unmarkIndex);
                            System.out.println("OK, I've marked this task as not done yet:");
                            System.out.println(tasks.get(unmarkIndex));
                            storage.save(tasks.getAll());
                        }
                        break;

                    case "todo":
                        if (parts.length < 2 || parts[1].trim().isEmpty()) {
                            System.out.println("OOPSIE!!! The description of a todo cannot be empty.");
                        } else {
                            tasks.add(new Todo(parts[1]));
                            System.out.println("Got it!! I've added this task:");
                            System.out.println("  " + tasks.get(tasks.size() - 1));
                            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                            storage.save(tasks.getAll());
                        }
                        break;

                    case "deadline":
                        if (parts.length < 2) {
                            System.out.println("OOPSIE!!! Usage: deadline <task> /by <time>");
                        } else if (!parts[1].contains("/by ")) {
                            System.out.println("OOPSIE!!! Please include '/by' followed by the deadline.");
                        } else {
                            /** We look for a space followed by a "by" and spilt along that to get two new string parts
                             * The same literal string matching regex is used in the events case below for words from and to
                             */
                            String[] dlParts = parts[1].split(" /by ", 2);
                            if (dlParts[0].trim().isEmpty() || dlParts[1].trim().isEmpty()) {
                                System.out.println("OOPSIE!!! Both description and deadline cannot be empty.");
                            } else {
                                tasks.add(new Deadline(dlParts[0].trim(), dlParts[1].trim()));
                                System.out.println("Got it!! I've added this task:");
                                System.out.println("  " + tasks.get(tasks.size() - 1));
                                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                                storage.save(tasks.getAll());
                            }
                        }
                        break;

                    case "event":
                        if (parts.length < 2) {
                            System.out.println("OOPSIE!!! Usage: event <task> /from <start> /to <end>");
                        } else if (!parts[1].contains("/from ") || !parts[1].contains("/to ")) {
                            System.out.println("OOPSIE!!! Please include both '/from' and '/to' for event times.");
                        } else {
                            String temp = parts[1];
                            String[] descParts = temp.split(" /from ", 2);
                            if (descParts.length < 2) {
                                System.out.println("OOPSIE!!! Invalid event format.");
                                break;
                            }
                            String desc = descParts[0].trim();
                            String[] timeParts = descParts[1].split(" /to ", 2);
                            if (timeParts.length < 2 || desc.isEmpty() || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
                                System.out.println("OOPSIE!!! Description, start time, and end time cannot be empty.");
                            } else {
                                tasks.add(new Event(desc, timeParts[0].trim(), timeParts[1].trim()));
                                System.out.println("Got it. I've added this task:");
                                System.out.println("  " + tasks.get(tasks.size() - 1));
                                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                                storage.save(tasks.getAll());
                            }
                        }
                        break;

                    case "delete":
                        if (parts.length < 2 || parts[1].trim().isEmpty()) {
                            System.out.println("OOPSIE!!! Please specify a task number to delete.");
                            break;  // ← prevents accessing parts[1] when it doesn't exist
                        }

                        try {
                            int index = Integer.parseInt(parts[1].trim()) - 1;
                            if (index < 0 || index >= tasks.size()) {
                                System.out.println("OOPSIE!!! Invalid task number.");
                            } else {
                                Task removed = tasks.remove(index);
                                System.out.println("Noted. I've removed this task:");
                                System.out.println(removed);
                                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                                storage.save(tasks.getAll());
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("OOPSIE!!! Please enter a valid number for the task.");
                        }
                        break;

                    default:
                        System.out.println("OOPSIE!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (NumberFormatException e) {
                System.out.println("OOPSIE!!! Please enter a valid number for the task.");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("OOPSIE!!! Invalid format or missing argument.");
            } catch (Exception e) {
                System.out.println("OOPSIE!!! Something went wrong: " + e.getMessage());
            }
        }
    }
}