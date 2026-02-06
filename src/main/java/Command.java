public abstract class Command {

    /**
     * Executes the command using the provided dependencies.
     *
     * @param tasks    the task list to operate on
     * @param ui       the user interface for displaying messages
     * @param storage  the storage handler for saving changes
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    /**
     * Indicates whether this command should terminate the program.
     *
     * @return true if this is an exit command, false otherwise
     */
    public abstract boolean isExit();
}
