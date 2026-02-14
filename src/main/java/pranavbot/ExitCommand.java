package pranavbot;

import pranavbot.Command;
import pranavbot.Storage;
import pranavbot.TaskList;
import pranavbot.IUi;

/**
 * pranavbot.Command that exits the program.
 */
public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, IUi ui, Storage storage) {
        storage.save(tasks.getAll());
        ui.showGoodbye();
        ui.closeApp();  // This calls the IUi method we added
    }

    @Override
    public boolean isExit() {
        return true;
    }
}  // ← Make sure this closing brace is there!

