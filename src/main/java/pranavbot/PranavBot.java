package pranavbot;

import java.util.List;

import pranavbot.task.Task;

/**
 * Core engine for PranavBot – processes commands and manages tasks.
 */
public class PranavBot {

    private final TaskList tasks;
    private final Storage storage;
    private final IUi ui;

    public PranavBot(IUi ui) {
        this.ui = ui;
        this.storage = new Storage("data/tasks.txt");
        List<Task> loaded = storage.load();
        this.tasks = new TaskList(loaded);
        ui.showLine();
        System.out.println("Loaded " + loaded.size() + " tasks from file.");
        ui.showLine();
    }

    /**
     * Processes a single command string and returns the response message.
     */
    public String processCommand(String fullCommand) {
        if (fullCommand.trim().isEmpty()) {
            return "Please enter a command.";
        }

        Command command = Parser.parse(fullCommand);
        command.execute(tasks, ui, storage);

        // Return a summary (customize later to collect full output)
        return "Command processed.";
    }

    public TaskList getTasks() {
        return tasks;
    }

    public Storage getStorage() {
        return storage;
    }

    /**
     * Text UI mode (fallback/console version).
     */
    public static void main(String[] args) {
        Ui textUi = new Ui();
        PranavBot bot = new PranavBot(textUi);
        textUi.showWelcome();

        while (true) {
            String fullCommand = textUi.readCommand();
            if (fullCommand.trim().isEmpty()) {
                textUi.showError("Please enter a command.");
                continue;
            }
            bot.processCommand(fullCommand);
            // Check exit after execution (adjust based on your Command design)
            // Note: You may need to track exit state differently in GUI mode
        }
    }
}

