/**
 * Command that marks a task as not done.
 */
public class UnmarkCommand extends Command {
    private final String argument;

    public UnmarkCommand(String argument) {
        this.argument = argument.trim();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (argument.isEmpty()) {
            ui.showError("Please specify a task number.");
            return;
        }

        try {
            int index = Integer.parseInt(argument) - 1;
            tasks.unmark(index);
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println(tasks.get(index));
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
