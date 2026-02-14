package pranavbot;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pranavbot.task.Task;
import pranavbot.task.Todo;
import pranavbot.task.Deadline;
import pranavbot.task.Event;

/**
 * The main entry point for PranavBot, a simple task management chatbot.
 * Orchestrates the application by initializing dependencies and running the main loop.
 */
public class PranavBot {
    private static final TaskList tasks = new TaskList();
    private static final Storage storage = new Storage("data/tasks.txt");

    static {
        /**
         * Static initializer that loads tasks from storage on application startup.
         */
        List<Task> loaded = storage.load();
        tasks.addAll(loaded);
        System.out.println("Loaded " + loaded.size() + " tasks from file.");
    }

    /**
     * Runs the main application loop, displaying the welcome message and processing user commands.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        TaskList tasks = new TaskList(storage.load());

        ui.showWelcome();

        while (true) {
            String fullCommand = ui.readCommand();

            if (fullCommand.trim().isEmpty()) {
                ui.showError("Please enter a command.");
                continue;
            }

            Command command = Parser.parse(fullCommand);
            command.execute(tasks, ui, storage);

            if (command.isExit()) {
                break;
            }
        }
    }
}