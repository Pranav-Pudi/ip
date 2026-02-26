package pranavbot;

import pranavbot.Storage;
import pranavbot.TaskList;
import pranavbot.Ui;

import pranavbot.task.Task;
import pranavbot.task.Todo;
import pranavbot.task.Deadline;
import pranavbot.task.Event;

/**
 * Command that adds a Deadline task.
 */
public class AddDeadlineCommand extends Command {
    private final String argument;

    public AddDeadlineCommand(String argument) {
        this.argument = argument.trim();
    }

    @Override
    public void execute(TaskList tasks, IUi ui, Storage storage) {
        if (argument.isEmpty() || !argument.contains("/by ")) {
            ui.showError("Usage: deadline <task> /by <time>");
            return;
        }

        String[] parts = argument.split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            ui.showError("Both description and deadline cannot be empty.");
            return;
        }

        try {
            Deadline deadline = new Deadline(parts[0].trim(), parts[1].trim());
            tasks.add(deadline);
            ui.showMessage("Got it!! I've added this task:\n  " + deadline);
            ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
            if (storage != null) {
                storage.save(tasks.getAll());
            }
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
