package pranavbot.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

import java.util.ArrayList;


import pranavbot.IUi;

public class GuiUi implements IUi {

    private final VBox dialogContainer;
    private final Image userImage;
    private final Image botImage;

    public GuiUi(VBox dialogContainer, Image userImage, Image botImage) {
        this.dialogContainer = dialogContainer;
        this.userImage = userImage;
        this.botImage = botImage;
    }

    @Override
    public void showWelcome() {
        appendMessage("Hello! I'm PranavBot.\nWhat can I do for you?", false);
    }

    @Override
    public void showError(String message) {
        appendMessage("OOPSIE!!! " + message, false);
    }

    @Override
    public void showGoodbye() {
        appendMessage("Bye. Hope to see you again soon!", false);
    }

    @Override
    public void showLine() {
        // remove horizontal line for GUI
    }

    /**
     * Appends a message to the GUI.
     * Multi-line messages are split into separate dialog bubbles.
     *
     * @param message the text
     * @param isUser true = user, false = bot
     */
    public void appendMessage(String message, boolean isUser) {

        DialogBox dialog = new DialogBox(message, isUser ? userImage : botImage);

        if (isUser) {
            dialog.flip();
        }

        dialogContainer.getChildren().add(dialog);

        FadeTransition fade = new FadeTransition(Duration.millis(250), dialog);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    @Override
    public void appendMessages(ArrayList<String> messages, boolean isUser) {
        String combined = String.join("\n", messages);
        appendMessage(combined, isUser);
    }

    // Inner class for chat bubbles
    public static class DialogBox extends HBox {
        private final Label text;
        private final ImageView displayPicture;

        public DialogBox(String message, Image img) {
            text = new Label(message);
            text.setWrapText(true);
            text.setMaxWidth(350);

            text.setStyle(
                    "-fx-background-color: #E5E5EA;" +
                            "-fx-background-radius: 15;" +
                            "-fx-padding: 10;"
            );
            displayPicture = img != null ? new ImageView(img) : new ImageView();
            displayPicture.setFitWidth(40);
            displayPicture.setFitHeight(40);

            this.setSpacing(10);
            this.getChildren().addAll(displayPicture, text);
            this.setAlignment(Pos.TOP_LEFT);
        }

        public void flip() {
            this.setAlignment(Pos.TOP_RIGHT);
            FXCollections.reverse(this.getChildren());

            text.setStyle(
                    "-fx-background-color: #0B93F6;" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 15;" +
                            "-fx-padding: 10;"
            );
        }
    }

    @Override
    public void showMessage(String message) {
        appendMessage(message, false); // bot message
    }

    @Override
    public void closeApp() {
        Platform.runLater(() -> {
            Stage stage = (Stage) dialogContainer.getScene().getWindow();
            stage.close();
        });
    }
}
