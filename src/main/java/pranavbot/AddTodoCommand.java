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
 * pranavbot.Command that adds a Todo task.
 */
public class AddTodoCommand extends Command {
    private final String argument;

    public AddTodoCommand(String argument) {
        this.argument = argument.trim();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (argument.isEmpty()) {
            ui.showError("The description of a todo cannot be empty.");
            return;
        }

        Todo todo = new Todo(argument);
        tasks.add(todo);
        System.out.println("Got it!! I've added this task:");
        System.out.println("  " + todo);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        storage.save(tasks.getAll());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
