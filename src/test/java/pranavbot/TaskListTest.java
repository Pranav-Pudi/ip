package pranavbot;

import org.junit.jupiter.api.Test;
import pranavbot.task.Todo;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    @Test
    public void addTask_taskAdded_sizeIncreases() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        assertEquals(1, tasks.size());
    }

    @Test
    public void markTask_taskMarked_statusIconIsX() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        tasks.mark(0);

        assertEquals("[X]", tasks.get(0).getStatusIcon());
    }

    @Test
    public void unmarkTask_taskUnmarked_statusIconIsSpace() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        tasks.mark(0);
        tasks.unmark(0);

        assertEquals("[ ]", tasks.get(0).getStatusIcon());
    }

    @Test
    public void removeTask_taskRemoved_sizeDecreases() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));

        tasks.remove(0);

        assertEquals(0, tasks.size());
    }

    @Test
    public void isEmpty_newList_returnsTrue() {
        TaskList tasks = new TaskList();

        assertTrue(tasks.isEmpty());
    }
}
