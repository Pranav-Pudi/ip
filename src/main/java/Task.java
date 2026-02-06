/**
 * Abstract parent class for all task types.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone = false;

    public Task(String description) {
        this.description = description;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public String getStatusIcon() {
        return isDone ? "[X]" : "[ ]";
    }

    // Used for storage later (Level-7+)
    public abstract String toFileFormat();

    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }

}
