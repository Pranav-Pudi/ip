package pranavbot;

import pranavbot.Command;
import pranavbot.Storage;
import pranavbot.TaskList;
import pranavbot.Ui;

/**
 * pranavbot.Command for unrecognized input.
 */
public class UnknownCommand extends Command {

    @Override
    public void execute(TaskList tasks, IUi ui, Storage storage) {
        ui.showError("I'm sorry, but I don't know what that means :-(");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
