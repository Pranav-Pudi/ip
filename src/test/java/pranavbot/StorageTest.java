package pranavbot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import pranavbot.task.Deadline;
import pranavbot.task.Event;
import pranavbot.task.Task;
import pranavbot.task.Todo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    private Storage storage;
    private Path tempFile;

    @BeforeEach
    void setUp(@TempDir Path tempDir) throws Exception {
        tempFile = tempDir.resolve("tasks-test.txt");
        storage = new Storage(tempFile.toString());
    }

    @Test
    void load_emptyFile_returnsEmptyList() {
        List<Task> tasks = storage.load();
        assertTrue(tasks.isEmpty());
    }

    @Test
    void save_and_load_singleTodo_roundTripsCorrectly() throws Exception {
        TaskList list = new TaskList();
        Todo todo = new Todo("buy groceries");
        System.out.println("Before save: " + todo.toFileFormat());  // ← add this
        list.add(todo);

        storage.save(list.getAll());

        List<Task> loaded = storage.load();

        assertEquals(1, loaded.size());
        System.out.println("After load: " + loaded.get(0).toFileFormat());  // ← add this
        assertEquals("T | 0 | buy groceries", loaded.get(0).toFileFormat());
    }

    @Test
    void load_validDeadlineAndEvent_parsesCorrectly() throws Exception {
        Files.writeString(tempFile,
                """
                T | 1 | finish report
                D | 0 | submit assignment | 2025-03-10
                E | 1 | team meeting | 2025-03-05 | 2025-03-05
                """);

        List<Task> loaded = storage.load();

        assertEquals(3, loaded.size());
        assertTrue(loaded.get(0) instanceof Todo);
        assertTrue(loaded.get(1) instanceof Deadline);
        assertTrue(loaded.get(2) instanceof Event);
    }

    @Test
    void load_malformedLine_skipsInvalidEntry() throws Exception {
        Files.writeString(tempFile,
                """
                T | 1 | valid todo
                X | 0 | broken type
                D | 0 | missing by part
                D | 0 | good deadline | 2025-03-15
                """);

        List<Task> loaded = storage.load();

        assertEquals(2, loaded.size());
        assertEquals("T | 1 | valid todo", loaded.get(0).toFileFormat());
        assertEquals("D | 0 | good deadline | 2025-03-15", loaded.get(1).toFileFormat());
    }
}
