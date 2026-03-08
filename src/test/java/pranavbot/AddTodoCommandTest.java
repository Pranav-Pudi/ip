package pranavbot;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddTodoCommandTest {

    @Test
    /** Tests valid todo creation - should add task and show success message */
    void addTodo_validDescription_addsTaskSuccessfully() {
        TaskList tasks = new TaskList();
        MockUi ui = new MockUi();
        AddTodoCommand cmd = new AddTodoCommand("buy milk");

        cmd.execute(tasks, ui, null);

        assertEquals(1, tasks.size());
        assertTrue(tasks.get(0).toString().contains("buy milk"));
        assertTrue(ui.messages.contains("Got it!!"));
    }

    @Test
    /** Tests empty todo - should show error, no task added */
    void addTodo_emptyDescription_showsErrorNoTaskAdded() {
        TaskList tasks = new TaskList();
        MockUi ui = new MockUi();
        AddTodoCommand cmd = new AddTodoCommand("");

        cmd.execute(tasks, ui, null);

        assertEquals(0, tasks.size());
        assertTrue(ui.messages.stream().anyMatch(msg ->
                msg.contains("todo cannot be empty")));
    }

    @Test
    public void execute_validTodo_taskAdded() {
        TaskList tasks = new TaskList();
        Storage storage = null;
        IUi ui = new Ui();

        AddTodoCommand cmd = new AddTodoCommand("read book");
        cmd.execute(tasks, ui, storage);

        assertEquals(1, tasks.size());
        assertEquals("read book", tasks.get(0).getDescription());
    }
}
