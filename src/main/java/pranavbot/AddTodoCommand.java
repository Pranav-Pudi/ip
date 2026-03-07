package pranavbot;

import pranavbot.Command;
import pranavbot.Storage;
import pranavbot.TaskList;
import pranavbot.Ui;

import pranavbot.task.Task;
import pranavbot.task.Todo;
import pranavbot.task.Deadline;
import pranavbot.task.Event;

import java.util.ArrayList;

/**
 * pranavbot.Command that adds a Todo task.
 */
public class AddTodoCommand extends Command {
    private final String argument;

    public AddTodoCommand(String argument) {
        this.argument = argument.trim();
    }

    @Override
    public void execute(TaskList tasks, IUi ui, Storage storage) {
        if (argument.isEmpty()) {
            ui.showError("The description of a todo cannot be empty.");
            return;
        }

        Todo todo = new Todo(argument);
        tasks.add(todo);
        ArrayList<String> output = new ArrayList<>();
        output.add("Got it!!");
        output.add("I've added this task:");
        output.add("  " + todo);
        output.add("Now you have " + tasks.size() + " tasks in the list.");
        ui.appendMessages(output, false);
        if (storage != null) {
            storage.save(tasks.getAll());
        }

    }

    @Override
    public boolean isExit() {
        return false;
    }
}
