package pranavbot;

import pranavbot.task.Deadline;
import pranavbot.task.Event;
import pranavbot.task.Task;
import pranavbot.task.Todo;

public class UpdateCommand extends Command {

    private final int targetIndex;      // 0-based
    private final String newDescription;
    private final String newBy;
    private final String newFrom;
    private final String newTo;

    public UpdateCommand(String fullArgument) {
        if (fullArgument == null || fullArgument.trim().isEmpty()) {
            throw new IllegalArgumentException("Usage: update <index> [desc ...] [by ...] [from ... to ...]");
        }

        String[] tokens = fullArgument.trim().split("\\s+", 2);
        try {
            this.targetIndex = Integer.parseInt(tokens[0]) - 1; // user uses 1-based indexing
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid task index. Please provide a number.");
        }

        String rest = tokens.length > 1 ? tokens[1].trim() : "";

        this.newDescription = extractValue(rest, "desc");
        this.newBy          = extractValue(rest, "by");
        this.newFrom        = extractValue(rest, "from");
        this.newTo          = extractValue(rest, "to");

        // At least one field must be provided
        if (newDescription == null && newBy == null && newFrom == null && newTo == null) {
            throw new IllegalArgumentException("No fields to update were provided.");
        }
    }

    /**
     * Extracts the value after a keyword (e.g. "desc ", "by ", "from ", "to ").
     * Returns null if keyword not found or value is empty.
     */
    private String extractValue(String input, String keyword) {
        String prefix = keyword + " ";
        int start = input.toLowerCase().indexOf(prefix.toLowerCase());
        if (start == -1) {
            return null;
        }
        start += prefix.length();

        // Find next keyword or end of string
        int end = input.length();
        for (String nextKey : new String[]{" desc ", " by ", " from ", " to "}) {
            int next = input.toLowerCase().indexOf(nextKey.toLowerCase(), start);
            if (next != -1 && next < end) {
                end = next;
            }
        }

        String value = input.substring(start, end).trim();
        return value.isEmpty() ? null : value;
    }

    @Override
    public void execute(TaskList tasks, IUi ui, Storage storage) {
        if (targetIndex < 0 || targetIndex >= tasks.size()) {
            ui.showError("Task number " + (targetIndex + 1) + " does not exist.");
            return;
        }

        Task oldTask = tasks.get(targetIndex);
        String descToUse = (newDescription != null) ? newDescription : oldTask.getDescription();

        Task updatedTask;

        try {
            if (oldTask instanceof Todo) {
                if (newBy != null || newFrom != null || newTo != null) {
                    ui.showError("Todo tasks cannot have by/from/to dates.");
                    return;
                }
                updatedTask = new Todo(descToUse);

            } else if (oldTask instanceof Deadline) {
                if (newFrom != null || newTo != null) {
                    ui.showError("Deadline tasks cannot have from/to dates.");
                    return;
                }
                String byToUse = (newBy != null) ? newBy : ((Deadline) oldTask).toFileFormat().split(" \\| ")[3];
                updatedTask = new Deadline(descToUse, byToUse);

            } else if (oldTask instanceof Event) {
                if (newBy != null) {
                    ui.showError("Event tasks cannot have a by date.");
                    return;
                }
                String[] parts = oldTask.toFileFormat().split(" \\| ");
                String fromToUse = (newFrom != null) ? newFrom : parts[3];
                String toToUse   = (newTo   != null) ? newTo   : parts[4];
                updatedTask = new Event(descToUse, fromToUse, toToUse);

            } else {
                ui.showError("Unknown task type.");
                return;
            }

            // Replace old task with updated one
            tasks.remove(targetIndex);
            tasks.add(updatedTask, targetIndex);  // insert at same position

            ui.showMessage("Updated task:");
            ui.showMessage("  " + updatedTask);
            ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");

            if (storage != null) {
                storage.save(tasks.getAll());
            }

        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
