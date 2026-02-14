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
    public void execute(TaskList tasks, IUi ui, Storage storage) {
        if (tasks.isEmpty()) {
            ui.showError("Your task list is empty.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Here are the tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append((i + 1)).append(".").append(tasks.get(i)).append("\n");
            }
            ui.showMessage(sb.toString());

        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
