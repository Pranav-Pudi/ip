package pranavbot;

import pranavbot.task.Task;

import java.util.ArrayList;
import java.util.List;

public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword.trim();
    }

    @Override
    public void execute(TaskList tasks, IUi ui, Storage storage) {
        if (keyword.isEmpty()) {
            ui.showError("Please provide a keyword to search.");
            return;
        }

        List<Task> matches = new ArrayList<>();
        for (Task t : tasks.getAll()) {
            if (t.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matches.add(t);
            }
        }

        if (matches.isEmpty()) {
            System.out.println("No matching tasks found.");
        } else {
            ui.showMessage("Here are the matching tasks in your list:");
            for (int i = 0; i < matches.size(); i++) {
                System.out.println((i + 1) + "." + matches.get(i));
            }
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
