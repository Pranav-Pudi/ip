package pranavbot;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void parse_todoCommand_returnsAddTodoCommand() {
        Command cmd = Parser.parse("todo read book");
        assertTrue(cmd instanceof AddTodoCommand);
    }

    @Test
    public void parse_listCommand_returnsListCommand() {
        Command cmd = Parser.parse("list");
        assertTrue(cmd instanceof ListCommand);
    }

    @Test
    public void parse_unknownCommand_returnsUnknownCommand() {
        Command cmd = Parser.parse("blahblah");
        assertTrue(cmd instanceof UnknownCommand);
    }

    @Test
    public void parse_extraSpaces_handlesCorrectly() {
        Command cmd = Parser.parse("   todo     read book   ");
        assertTrue(cmd instanceof AddTodoCommand);
    }
}

