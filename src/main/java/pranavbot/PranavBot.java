package pranavbot;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pranavbot.task.Task;
import pranavbot.task.Todo;
import pranavbot.task.Deadline;
import pranavbot.task.Event;

import pranavbot.Ui;
import pranavbot.Parser;
import pranavbot.Storage;
import pranavbot.TaskList;
import pranavbot.Command;

/**
 * The main entry point for PranavBot, a simple task management chatbot.
 */
public class PranavBot {

    private static final TaskList tasks;
    private static final Storage storage = new Storage("data/tasks.txt");

    static {
        // Load tasks after storage is initialized
        List<Task> loaded = storage.load();
        tasks = new TaskList(loaded);
        System.out.println("Loaded " + loaded.size() + " tasks from file.");
    }

    public static void main(String[] args) {
        Ui ui = new Ui();
        TaskList tasks = new TaskList(storage.load());

        ui.showWelcome();

        while (true) {
            String fullCommand = ui.readCommand();
            if (fullCommand == null) {
                // End of input stream (e.g., Gradle run without piped input)
                break;
            }

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