package pranavbot;

import java.util.ArrayList;
import java.util.List;

import pranavbot.task.Task;
import pranavbot.task.Todo;
import pranavbot.task.Deadline;
import pranavbot.task.Event;

/**
 * Manages the collection of tasks, providing operations to add, retrieve,
 * mark/unmark, delete, and list tasks.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> initialTasks) {
        this.tasks = new ArrayList<>(initialTasks);
    }

    /**
     * Adds a task to the list.
     * If index is provided, replaces the task at that position (0-based).
     * If index is -1 (default), appends the task to the end.
     *
     * @param task  the task to add or replace with
     * @param index optional 0-based index where to place/replace the task; use -1 to append
     * @throws IndexOutOfBoundsException if index is out of range (and not -1)
     */
    public void add(Task task, int index) {
        if (index == -1) {
            tasks.add(task);           // append to end (original behavior)
        } else {
            if (index < 0 || index >= tasks.size()) {
                throw new IndexOutOfBoundsException("Invalid task index: " + (index + 1));
            }
            tasks.set(index, task);    // replace at position
        }
    }

    /**
     * Convenience overload: appends a task to the end (original signature).
     */
    public void add(Task task) {
        add(task, -1);
    }

    public void addAll(List<Task> additionalTasks) {
        tasks.addAll(additionalTasks);
    }

    public Task get(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task number");
        }
        return tasks.get(index);
    }

    /**
     * Marks the task at the given 0-based index as done.
     * @param index 0-based index of the task
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public void mark(int index) {
        get(index).markAsDone();
    }

    public void unmark(int index) {
        get(index).markAsNotDone();
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public List<Task> getAll() {
        return new ArrayList<>(tasks); // Defensive copy
    }
}
