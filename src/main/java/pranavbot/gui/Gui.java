package pranavbot.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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

        // Layout
        dialogContainer = new VBox(10);
        scrollPane = new ScrollPane(dialogContainer);
        scrollPane.setFitToWidth(true);

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);
        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);
        AnchorPane.setBottomAnchor(sendButton, 40.0);

        scrollPane.setPrefSize(385, 535);
        userInput.setPrefWidth(325);
        sendButton.setPrefWidth(55);

        scene = new Scene(mainLayout, 400, 600);
        stage.setScene(scene);
        stage.setTitle("PranavBot Chat");
        stage.setResizable(false);
        stage.show();

        // GUI handler & bot
        guiUi = new GuiUi(dialogContainer, userImage, botImage);
        bot = new PranavBot(guiUi); // now bot writes directly to GuiUi

        guiUi.showWelcome();

        dialogContainer.heightProperty().addListener((obs) -> scrollPane.setVvalue(1.0));

        sendButton.setOnMouseClicked(event -> handleUserInput());
        userInput.setOnAction(event -> handleUserInput());
    }

    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) return;

        // Show user text
        guiUi.appendMessage(input, true);

        // Bot processes command and writes all messages to GUI directly
        bot.processCommand(input);

        userInput.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}







