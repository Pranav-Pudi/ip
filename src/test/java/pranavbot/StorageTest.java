package pranavbot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.util.List;

import pranavbot.task.Task;
import pranavbot.task.Todo;

public class StorageTest {

    private Storage storage;
    private Path tempFile;

    @BeforeEach
    public void setUp(@TempDir Path tempDir) {
        tempFile = tempDir.resolve("tasks.txt");
        storage = new Storage(tempFile.toString());
    }

    @Test
    public void testSaveAndLoad() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("Test task"));
        storage.save(tasks.getAll());

        List<Task> loaded = storage.load();
        assertEquals(1, loaded.size());
        assertTrue(loaded.get(0) instanceof Todo);
        assertEquals("Test task", loaded.get(0).getDescription());
    }

    @Test
    public void testLoadNonExistentFileReturnsEmpty() {
        List<Task> loaded = storage.load();
        assertTrue(loaded.isEmpty());
    }
}