package pranavbot;

import java.util.ArrayList;

public class MockUi implements IUi {

    public final ArrayList<String> messages = new ArrayList<>();

    @Override
    public void showWelcome() {
        messages.add("Welcome!");
    }

    @Override
    public void showError(String message) {
        messages.add(message);
    }

    @Override
    public void showGoodbye() {
        messages.add("Goodbye!");
    }

    @Override
    public void showLine() {
        // no-op
    }

    @Override
    public void showMessage(String message) {
        messages.add(message);
    }

    @Override
    public void appendMessages(ArrayList<String> messagesList, boolean isUser) {
        messages.addAll(messagesList); // this is critical
    }

    @Override
    public void closeApp() {
        // no-op
    }
}

