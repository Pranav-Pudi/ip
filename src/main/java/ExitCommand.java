/**
 * Command that exits the program.
 */
public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        storage.save(tasks.getAll());
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}