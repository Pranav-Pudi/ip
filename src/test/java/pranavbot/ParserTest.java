package pranavbot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parse_bye_returnsExitCommand() {
        Command cmd = Parser.parse("bye");
        assertTrue(cmd instanceof ExitCommand);
        assertTrue(cmd.isExit());
    }

    @Test
    void parse_list_returnsListCommand() {
        Command cmd = Parser.parse("list   ");
        assertTrue(cmd instanceof ListCommand);
        assertFalse(cmd.isExit());
    }

    @Test
    void parse_markWithNumber_returnsMarkCommand() {
        Command cmd = Parser.parse("mark 3");
        assertTrue(cmd instanceof MarkCommand);
    }

    @Test
    void parse_unknownCommand_returnsUnknownCommand() {
        Command cmd = Parser.parse("hello world");
        assertTrue(cmd instanceof UnknownCommand);
    }

    @Test
    void parse_todoWithDescription_returnsAddTodoCommand() {
        Command cmd = Parser.parse("todo read chapter 5");
        assertTrue(cmd instanceof AddTodoCommand);
    }

    @Test
    void parse_deadlineWithBy_returnsAddDeadlineCommand() {
        Command cmd = Parser.parse("deadline assignment /by 2025-04-15");
        assertTrue(cmd instanceof AddDeadlineCommand);
    }
}

