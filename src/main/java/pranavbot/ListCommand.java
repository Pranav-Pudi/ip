package pranavbot;

import pranavbot.Command;
import pranavbot.Storage;
import pranavbot.TaskList;
import pranavbot.Ui;

/**
 * pranavbot.Command that lists all tasks.
 */
public class ListCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.isEmpty()) {
            ui.showError("Your task list is empty.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}