package pranavbot.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pranavbot.TaskList;
import pranavbot.task.Todo;

public class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }

    @Test
    public void testAddAndSize() {
        assertTrue(taskList.isEmpty());
        assertEquals(0, taskList.size());

        taskList.add(new Todo("Test task"));
        assertFalse(taskList.isEmpty());
        assertEquals(1, taskList.size());
    }

    @Test
    public void testMarkAndGet() {
        Todo todo = new Todo("Finish report");
        taskList.add(todo);

        taskList.mark(0);
        assertTrue(taskList.get(0).isDone());

        taskList.unmark(0);
        assertFalse(taskList.get(0).isDone());
    }

    @Test
    public void testRemove() {
        Task task = new Todo("Remove me");
        taskList.add(task);

        Task removed = taskList.remove(0);
        assertEquals(task, removed);
        assertTrue(taskList.isEmpty());
    }

    @Test
    public void testInvalidIndexThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.mark(0));
    }
}
