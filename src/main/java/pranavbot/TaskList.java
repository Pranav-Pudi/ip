import java.util.ArrayList;
import java.util.List;

/**
 * Manages the collection of tasks, providing operations to add, retrieve, mark/unmark, delete, and list tasks.
 */
public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> initialTasks) {
        this.tasks = new ArrayList<>(initialTasks);
    }

    public void add(Task task) {
        tasks.add(task);
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
