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
        // For task lists, keep as ONE dialog box with line breaks
        if (message.contains("Here are the tasks") || message.contains("Here are the matching tasks")) {
            DialogBox dialog = new DialogBox(message, isUser ? userImage : botImage);  // Keep \n
            if (isUser) dialog.flip();
            dialogContainer.getChildren().add(dialog);
        } else {
            // Split other messages normally
            String[] lines = message.split("\n");
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    DialogBox dialog = new DialogBox(line, isUser ? userImage : botImage);
                    if (isUser) dialog.flip();
                    dialogContainer.getChildren().add(dialog);
                }
            }
        }
    }

    // Inner class for chat bubbles
    public static class DialogBox extends HBox {
        private final Label text;
        private final ImageView displayPicture;

        public DialogBox(String message, Image img) {
            text = new Label(message);
            text.setWrapText(true);

            displayPicture = img != null ? new ImageView(img) : new ImageView();
            displayPicture.setFitWidth(50);
            displayPicture.setFitHeight(50);

            this.setSpacing(10);
            this.getChildren().addAll(displayPicture, text);
            this.setAlignment(Pos.TOP_LEFT);
        }

        public void flip() {
            this.setAlignment(Pos.TOP_RIGHT);
            FXCollections.reverse(this.getChildren());
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







