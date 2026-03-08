package pranavbot.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.List;
import java.util.ArrayList;

import pranavbot.PranavBot;

public class Gui extends Application {

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;
    private GuiUi guiUi;
    private PranavBot bot;

    private Image userImage;
    private Image botImage;

    @Override
    public void start(Stage stage) {

        // Load avatars
        try {
            userImage = new Image(getClass().getResourceAsStream("/images/user.png"));
            botImage = new Image(getClass().getResourceAsStream("/images/bot.png"));
        } catch (Exception e) {
            System.err.println("Warning: avatars not found.");
            userImage = null;
            botImage = null;
        }

        // Chat container
        dialogContainer = new VBox(10);
        dialogContainer.setStyle("-fx-padding: 15;");

        scrollPane = new ScrollPane(dialogContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: white; -fx-border-color: transparent;");

        // Input area
        userInput = new TextField();
        userInput.setPromptText("Type a command...");

        sendButton = new Button("Send");
        sendButton.setStyle(
                "-fx-background-color: #4CAF50;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;"
        );

        HBox inputBar = new HBox(10, userInput, sendButton);
        inputBar.setStyle(
                "-fx-padding: 10;" +
                        "-fx-background-color: #f5f5f5;"
        );

        // Make text field expand
        HBox.setHgrow(userInput, Priority.ALWAYS);

        // Root layout
        VBox root = new VBox(scrollPane, inputBar);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        scene = new Scene(root, 450, 650);

        stage.setTitle("Pixel Chatbot 🟦 - Task Assistant");
        stage.setScene(scene);
        stage.show();

        // Initialize bot
        guiUi = new GuiUi(dialogContainer, userImage, botImage);
        bot = new PranavBot(guiUi);

        guiUi.showWelcome();

        // Auto scroll
        dialogContainer.heightProperty().addListener(
                (observable) -> scrollPane.setVvalue(1.0)
        );

        // Input handlers
        sendButton.setOnAction(event -> handleUserInput());
        userInput.setOnAction(event -> handleUserInput());

        HBox titleBar = new HBox();
        titleBar.setStyle("-fx-background-color: #0B93F6; -fx-padding: 10;");
        Label titleLabel = new Label("Pixel Chatbot 🟦 - Task Assistant");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        titleBar.getChildren().add(titleLabel);
    }

    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) return;

        guiUi.appendMessage(input, true);

        PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
        pause.setOnFinished(event -> bot.processCommand(input));
        pause.play();

        userInput.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
