package pranavbot;

public interface IUi {
    void showWelcome();
    void showLine();
    void showError(String message);
    void showGoodbye();
    void showMessage(String message);
    void closeApp();
}

