package pranavbot;

import java.util.ArrayList;

public interface IUi {
    void showWelcome();
    void showLine();
    void showError(String message);
    void showGoodbye();
    void showMessage(String message);
    void closeApp();

    void appendMessages(ArrayList<String> output, boolean b);
}

