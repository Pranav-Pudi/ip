package pranavbot;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExitCommandTest {

    @Test
    void exitCommand_showsGoodbyeAndSignalsExit() {
        TaskList tasks = new TaskList();
        MockUi ui = new MockUi();
        Storage storage = null;
        ExitCommand cmd = new ExitCommand();

        cmd.execute(tasks, ui, storage);

        boolean foundGoodbye = ui.messages.stream()
                .anyMatch(msg -> msg.toLowerCase().contains("bye"));
        assertTrue(foundGoodbye);
        assertTrue(cmd.isExit());
    }
}

