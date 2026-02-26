package pranavbot;

import pranavbot.task.*;

public class UpdateCommand extends Command {
    private final int targetIndex;          // 1-based from user input
    private final String newDescription;
    private final String newBy;             // for Deadline
    private final String newFrom;           // for Event
    private final String newTo;             // for Event
    private final String newPriority;       // optional – only if you want to add priority later

    public UpdateCommand(String arguments) {
        // Parse arguments – very similar to how AddDeadlineCommand / AddEventCommand parse
        String[] parts = arguments.trim().split("\\s+", 2);
        if (parts.length < 2) {
            throw new IllegalArgumentException("Usage: update INDEX [desc ...] [by ...] [from ... to ...]");
        }

        try {
            this.targetIndex = Integer.parseInt(parts[0]) - 1;  // 0-based internally
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid task index.");
        }

        String rest = parts.length > 1 ? parts[1] : "";

        // Very basic keyword-based parsing (can be improved later)
        this.newDescription = extractField(rest, "desc");
        this.newBy         = extractField(rest, "by");
        this.newFrom       = extractField(rest, "from");
        this.newTo         = extractField(rest, "to");
        this.newPriority   = extractField(rest, "priority");  // stub for future

        if (newDescription == null && newBy == null && newFrom == null && newTo == null) {
            throw new IllegalArgumentException("No fields to update were provided.");
        }
    }

    private String extractField(String input, String keyword) {
        String pattern = keyword + " ";
        int start = input.indexOf(pattern);
        if (start == -1) return null;

        start += pattern.length();
        int end = input.indexOf(" ", start);
        if (end == -1) end = input.length();

        String value = input.substring(start, end).trim();
        return value.isEmpty() ? null : value;
    }

    @Override
    public void execute(TaskList tasks, IUi ui, Storage storage) {
        if (targetIndex < 0 || targetIndex >= tasks.size()) {
            ui.showError("Invalid task number.");
            return;
        }

        Task task = tasks.get(targetIndex);

        try {
            // Update description if provided
            if (newDescription != null) {
                task = createUpdatedTask(task, newDescription, newBy, newFrom, newTo);
            }

            // Special handling for type-specific fields
            if (newBy != null && !(task instanceof Deadline)) {
                ui.showError("Only deadline tasks can have a 'by' field updated.");
                return;
            }
            if ((newFrom != null || newTo != null) && !(task instanceof Event)) {
                ui.showError("Only event tasks can have 'from'/'to' fields updated.");
                return;
            }

            // Apply changes
            if (newBy != null) {
                ((Deadline) task).updateBy(newBy);  // need to add this method
            }
            if (newFrom != null || newTo != null) {
                ((Event) task).updateTimes(newFrom, newTo);  // need to add this method
            }

            ui.showMessage("Updated task:\n  " + task);
            if (storage != null) {
                storage.save(tasks.getAll());
            }
        } catch (IllegalArgumentException e) {
            ui.showError(e.getMessage());
        }
    }

    private Task createUpdatedTask(Task original, String desc, String by, String from, String to) {
        // For simplicity, we create a new instance with updated description
        // (preserves type and other fields)
        if (original instanceof Todo) {
            return new Todo(desc != null ? desc : original.getDescription());
        } else if (original instanceof Deadline) {
            String oldBy = ((Deadline) original).getBy().toString();
            return new Deadline(desc != null ? desc : original.getDescription(),
                    by != null ? by : oldBy);
        } else if (original instanceof Event) {
            String oldFrom = ((Event) original).getFrom().toString();
            String oldTo   = ((Event) original).getTo().toString();
            return new Event(desc != null ? desc : original.getDescription(),
                    from != null ? from : oldFrom,
                    to   != null ? to   : oldTo);
        }
        throw new IllegalStateException("Unknown task type");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
