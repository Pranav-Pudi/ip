package pranavbot;

import pranavbot.gui.Gui;

/**
 * Launcher class required for JavaFX applications to work correctly as executable JARs.
 */
public class Launcher {
    public static void main(String[] args) {
        javafx.application.Application.launch(Gui.class, args);
    }
}
