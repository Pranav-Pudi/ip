package pranavbot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pranavbot.task.Task;

import static org.junit.jupiter.api.Assertions.*;

class AddDeadlineCommandTest {

    private TaskList tasks;
    private MockUi ui;

    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        ui = new MockUi();
    }

    @Test
    void execute_validDeadline_addsTaskAndSaves() {
        AddDeadlineCommand cmd = new AddDeadlineCommand("submit report /by 2025-03-20");

        cmd.execute(tasks, ui, null);  // null storage for this test

        assertEquals(1, tasks.size());
        Task added = tasks.get(0);
        assertTrue(added.toString().contains("submit report"));
        assertTrue(added.toString().contains("2025-03-20"));
        assertTrue(ui.messages.stream().anyMatch(m -> m.contains("Got it")));
    }

    @Test
    void execute_missingByPart_showsErrorNoTaskAdded() {
        AddDeadlineCommand cmd = new AddDeadlineCommand("project /by");

        cmd.execute(tasks, ui, null);

        assertEquals(0, tasks.size());

        assertTrue(ui.messages.stream().anyMatch(m -> m.contains("/by")),
                "Expected usage instructions mentioning /by keyword");
    }

    @Test
    void execute_invalidDateFormat_showsError() {
        AddDeadlineCommand cmd = new AddDeadlineCommand("exam /by tomorrow");

        cmd.execute(tasks, ui, null);

        assertEquals(0, tasks.size());
        assertTrue(ui.messages.stream().anyMatch(m -> m.contains("Invalid date format")));
    }
}
