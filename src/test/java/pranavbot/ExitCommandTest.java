package pranavbot;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExitCommandTest {

    @Test
    /** Tests bye command shows goodbye and signals exit */
    void exitCommand_showsGoodbyeAndSignalsExit() {
        TaskList tasks = new TaskList();
        MockUi ui = new MockUi();
        Storage storage = null;
        ExitCommand cmd = new ExitCommand();

        cmd.execute(tasks, ui, storage);

        assertTrue(ui.messages.contains("goodbye"));
        assertTrue(cmd.isExit());
    }
}

