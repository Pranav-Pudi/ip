package pranavbot;

/**
 * Represents a parser that interprets user input commands.
 * Handles the parsing of raw string input into executable Command objects.
 */

public class Parser {
    /**
     * Parses the full command string provided by the user and returns the corresponding
     * command object to be executed.
     *
     * @param fullCommand The raw input string from the user.
     * @return A {@link Command} object corresponding to the parsed command word.
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
            case "cheer":
                return new CheerCommand();
            case "find":
                return new FindCommand(parts.length > 1 ? parts[1] : "");
            default:
                return new UnknownCommand();
        }
    }
}
