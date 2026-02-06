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