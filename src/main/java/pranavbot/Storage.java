package pranavbot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import pranavbot.task.Task;
import pranavbot.task.Todo;
import pranavbot.task.Deadline;
import pranavbot.task.Event;

/**
 * Manages persistent storage of tasks using a plain-text file.
 * <p>
 * This class handles reading tasks from the file on startup and writing
 * the current task list back to the file after any modification.
 * It uses a simple pipe-delimited format and skips malformed lines silently.
 * </p>
 */
public class Storage {

    private static final String TASK_FILE_PATH = "data/tasks.txt";
    private static final String FIELD_DELIMITER = " | ";
    private static final String DONE_INDICATOR = "1";
    private static final String TYPE_TODO = "T";
    private static final String TYPE_DEADLINE = "D";
    private static final String TYPE_EVENT = "E";

    private final Path filePath;

    /**
     * Constructs a Storage instance for the default task file.
     * Creates parent directories and the file itself if they do not exist.
     */
    public Storage() {
        this.filePath = Paths.get(TASK_FILE_PATH);
        try {
            Files.createDirectories(filePath.getParent());
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            System.out.println("OOPSIE!!! Error creating data file/directory: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the storage file.
     * <p>
     * Empty lines and malformed entries are skipped.
     * If the file does not exist, an empty list is returned.
     * </p>
     *
     * @return list of parsed tasks (never null)
     */
    public List<Task> load() {
        List<Task> loadedTasks = new ArrayList<>();

        if (!Files.exists(filePath)) {
            return loadedTasks;
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(FIELD_DELIMITER);
                if (parts.length < 3) {
                    continue;
                }

                String type = parts[0].trim();
                boolean isDone = DONE_INDICATOR.equals(parts[1].trim());
                String description = parts[2].trim();

                Task task = createTaskFromParts(type, description, parts);
                if (task != null) {
                    if (isDone) {
                        task.markAsDone();
                    }
                    loadedTasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("OOPSIE!!! Error loading tasks from file: " + e.getMessage());
        }

        return loadedTasks;
    }

    private Task createTaskFromParts(String type, String description, String[] parts) {
        switch (type) {
            case TYPE_TODO:
                return new Todo(description);
            case TYPE_DEADLINE:
                return parts.length >= 4 ? new Deadline(description, parts[3].trim()) : null;
            case TYPE_EVENT:
                return parts.length >= 5 ? new Event(description, parts[3].trim(), parts[4].trim()) : null;
            default:
                return null;
        }
    }

    /**
     * Saves the provided list of tasks to the storage file.
     * <p>
     * The file is completely overwritten. Each task is written in its file format
     * on a separate line.
     * </p>
     *
     * @param tasks the tasks to save (must not be null)
     */
    public void save(List<Task> tasks) {
        assert tasks != null : "Cannot save null task list";

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (Task task : tasks) {
                writer.write(task.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("OOPSIE!!! Error saving tasks to file: " + e.getMessage());
        }
    }
}