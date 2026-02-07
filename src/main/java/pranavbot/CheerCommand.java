package pranavbot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class CheerCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            List<String> quotes = Files.readAllLines(Paths.get("data/cheer.txt"));
            if (quotes.isEmpty()) {
                ui.showError("No quotes available.");
                return;
            }

            Random random = new Random();
            String quote = quotes.get(random.nextInt(quotes.size())).trim();
            System.out.println("____________________________________________________________");
            System.out.println(quote);
            System.out.println("____________________________________________________________");
        } catch (IOException e) {
            ui.showError("Error loading quotes: " + e.getMessage());
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}