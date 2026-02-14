package pranavbot;
import java.util.ArrayList;
import java.util.List;

public class MockUi implements IUi {
    public List<String> messages = new ArrayList<>();

    @Override public void showWelcome() { messages.add("welcome"); }
    @Override public void showLine() { messages.add("line"); }
    @Override public void showError(String msg) { messages.add("ERROR: " + msg); }
    @Override public void showGoodbye() { messages.add("goodbye"); }
    @Override public void showMessage(String msg) { messages.add(msg); }
    @Override public void closeApp() { }
}

