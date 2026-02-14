package pranavbot.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a single chat message with optional avatar.
 */
public class DialogBox extends HBox {
    private final Label text;
    private final ImageView displayPicture;

    public DialogBox(String message, Image img) {
        text = new Label(message);
        text.setWrapText(true);
        text.setMaxWidth(300);  // Reasonable width for chat bubble
        text.setStyle("-fx-padding: 10;");

        displayPicture = img != null ? new ImageView(img) : new ImageView();
        displayPicture.setFitWidth(40);
        displayPicture.setFitHeight(40);

        this.setAlignment(Pos.TOP_RIGHT); // default right-align (user)
        this.getChildren().addAll(text, displayPicture);
    }

    /** Flip for left-aligned (bot) messages */
    public void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        this.getChildren().setAll(displayPicture, text);
    }
}



