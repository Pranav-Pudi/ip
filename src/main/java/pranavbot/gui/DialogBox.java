package pranavbot.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.collections.FXCollections;

/**
 * Represents a single chat message with optional avatar.
 */
public class DialogBox extends HBox {
    private final Label text;
    private final ImageView displayPicture;

    public DialogBox(String message, Image img) {
        text = new Label(message);
        text.setWrapText(true);
        text.setMaxWidth(320);

        text.setStyle(
                "-fx-background-color: #f1f1f1;" +
                        "-fx-padding: 10;" +
                        "-fx-background-radius: 12;"
        );

        displayPicture = img != null ? new ImageView(img) : new ImageView();
        displayPicture.setFitWidth(40);
        displayPicture.setFitHeight(40);

        this.setSpacing(10);
        this.setAlignment(Pos.TOP_LEFT);
        this.setStyle("-fx-padding: 5;");
        this.getChildren().addAll(displayPicture, text);
    }

    /** Flip for left-aligned (bot) messages */
    public void flip() {
        this.setAlignment(Pos.TOP_RIGHT);

        text.setStyle(
                "-fx-background-color: #d0e6ff;" +
                        "-fx-padding: 10;" +
                        "-fx-background-radius: 12;"
        );

        FXCollections.reverse(this.getChildren());
    }
}



