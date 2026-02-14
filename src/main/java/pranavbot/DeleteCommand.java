package pranavbot;

import pranavbot.Command;
import pranavbot.Storage;
import pranavbot.TaskList;
import pranavbot.Ui;

import pranavbot.task.Task;
import pranavbot.task.Todo;
import pranavbot.task.Deadline;
import pranavbot.task.Event;

/**
 * pranavbot.Command that deletes a task by index.
 */
public class DeleteCommand extends Command {
    private final String argument;

    public DeleteCommand(String argument) {
        this.argument = argument.trim();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (argument.isEmpty()) {
            ui.showError("Please specify a task number to delete.");
            return;
        }

        try {
            int index = Integer.parseInt(argument) - 1;
            Task removed = tasks.remove(index);
            System.out.println("Noted. I've removed this task:");
            System.out.println(removed);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
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
