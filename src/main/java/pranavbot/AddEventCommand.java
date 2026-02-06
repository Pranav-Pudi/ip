import pranavbot.Command;
import pranavbot.Storage;
import pranavbot.TaskList;
import pranavbot.Ui;

/**
 * pranavbot.Command that adds an Event task.
 */
public class AddEventCommand extends Command {
    private final String argument;

    public AddEventCommand(String argument) {
        this.argument = argument.trim();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (argument.isEmpty() || !argument.contains("/from ") || !argument.contains("/to ")) {
            ui.showError("Usage: event <task> /from <start> /to <end>");
            return;
        }

        String[] fromParts = argument.split(" /from ", 2);
        if (fromParts.length < 2) {
            ui.showError("Invalid event format.");
            return;
        }

        String desc = fromParts[0].trim();
        String[] timeParts = fromParts[1].split(" /to ", 2);
        if (timeParts.length < 2 || desc.isEmpty() || timeParts[0].trim().isEmpty() || timeParts[1].trim().isEmpty()) {
            ui.showError("Description, start time, and end time cannot be empty.");
            return;
        }

        try {
            Event event = new Event(desc, timeParts[0].trim(), timeParts[1].trim());
            tasks.add(event);
            System.out.println("Got it. I've added this task:");
            System.out.println("  " + event);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            storage.save(tasks.getAll());
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
