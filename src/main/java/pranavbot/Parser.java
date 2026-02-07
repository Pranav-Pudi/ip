package pranavbot;

/**
 * Parses user input strings into executable Command objects.
 */

public class Parser {

    /**
     * Parses a full user command string into the appropriate Command object.
     *
     * @param fullCommand the raw input string from the user
     * @return the corresponding Command instance
     */
    public static Command parse(String fullCommand) {
        String[] parts = fullCommand.trim().split("\\s+", 2);
        String commandWord = parts[0].toLowerCase();

        switch (commandWord) {
            case "bye":
                return new ExitCommand();
            case "list":
                return new ListCommand();
            case "mark":
                return new MarkCommand(parts.length > 1 ? parts[1] : "");
            case "unmark":
                return new UnmarkCommand(parts.length > 1 ? parts[1] : "");
            case "delete":
                return new DeleteCommand(parts.length > 1 ? parts[1] : "");
            case "todo":
                return new AddTodoCommand(parts.length > 1 ? parts[1] : "");
            case "deadline":
                return new AddDeadlineCommand(parts.length > 1 ? parts[1] : "");
            case "event":
                return new AddEventCommand(parts.length > 1 ? parts[1] : "");
            default:
                return new UnknownCommand();
        }
    }
}
