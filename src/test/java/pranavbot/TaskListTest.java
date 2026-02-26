package pranavbot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pranavbot.task.Task;
import pranavbot.task.Todo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    @Test
    void add_singleTask_increasesSizeAndRetrievesCorrectly() {
        Task task = new Todo("read book");
        taskList.add(task);

        assertEquals(1, taskList.size());
        assertSame(task, taskList.get(0));
    }

    @Test
    void mark_validIndex_marksTaskAsDone() {
        Task task = new Todo("write report");
        taskList.add(task);

        taskList.mark(0);

        assertTrue(taskList.get(0).getStatusIcon().contains("X"));
    }

    @Test
    void mark_invalidIndex_throwsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.mark(0));
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.mark(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.mark(5));
    }

    @Test
    void remove_validIndex_returnsRemovedTaskAndDecreasesSize() {
        Task taskA = new Todo("task A");
        Task taskB = new Todo("task B");
        taskList.add(taskA);
        taskList.add(taskB);

        Task removed = taskList.remove(0);

        assertSame(taskA, removed);
        assertEquals(1, taskList.size());
        assertSame(taskB, taskList.get(0));
    }

    @Test
    void getAll_returnsDefensiveCopy_modifyingCopyDoesNotAffectOriginal() {
        taskList.add(new Todo("original task"));

        List<Task> copy = taskList.getAll();
        copy.add(new Todo("malicious task"));

        assertEquals(1, taskList.size());
        assertEquals(2, copy.size());
    }
}
