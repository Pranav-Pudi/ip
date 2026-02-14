package pranavbot;

import pranavbot.Command;
import pranavbot.Storage;
import pranavbot.TaskList;
import pranavbot.Ui;

/**
 * pranavbot.Command that marks a task as done.
 */
public class MarkCommand extends Command {
    private final String argument;

    public MarkCommand(String argument) {
        this.argument = argument.trim();
    }

    @Override
    public void execute(TaskList tasks, IUi ui, Storage storage) {
        if (argument.isEmpty()) {
            ui.showError("Please specify a task number.");
            return;
        }

        try {
            int index = Integer.parseInt(argument) - 1;
            tasks.mark(index);
            ui.showMessage(
                    "Nice!! I've marked this task as done:\n"
                            + "  " + tasks.get(index)
            );
            storage.save(tasks.getAll());
        } catch (NumberFormatException e) {
            ui.showError("Please enter a valid number for the task.");
        } catch (IndexOutOfBoundsException e) {
            ui.showError("Invalid task number.");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
