import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading tasks from and saving tasks to a file.
 */
public class Storage {
    private final Path filePath;

    public Storage(String filePathString) {
        this.filePath = Paths.get(filePathString);
        // Create parent directories and file if they do not exist
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
     * Loads tasks from the file.
     * @return List of loaded tasks (empty list if file is missing or empty)
     */
    public List<Task> load() {
        List<Task> loadedTasks = new ArrayList<>();

        if (!Files.exists(filePath)) {
            return loadedTasks; // File doesn't exist yet → start empty
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(" \\| ");
                if (parts.length < 3) continue; // Skip malformed lines

                String type = parts[0].trim();
                boolean isDone = "1".equals(parts[1].trim());
                String description = parts[2].trim();

                Task task = null;
                switch (type) {
                    case "T":
                        task = new Todo(description);
                        break;
                    case "D":
                        if (parts.length >= 4) {
                            task = new Deadline(description, parts[3].trim());
                        }
                        break;
                    case "E":
                        if (parts.length >= 5) {
                            task = new Event(description, parts[3].trim(), parts[4].trim());
                        }
                        break;
                    default:
                        // Skip unknown types
                        break;
                }

                if (task != null) {
                    if (isDone) task.markAsDone();
                    loadedTasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("OOPSIE!!! Error loading tasks from file: " + e.getMessage());
        }

        return loadedTasks;
    }

    /**
     * Saves the current list of tasks to the file.
     * @param tasks the list of tasks to save
     */
    public void save(List<Task> tasks) {
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
