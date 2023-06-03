package example.acht;

import example.managers.Argument;
import example.managers.Invoker;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ResourceBundle;

public class ChatTab extends Tab {

    private final Invoker serverCommunicator;
    private final String username;
    private final String password;
    private final Stage stage;
    private final ResourceBundle bundle;
    private TextArea chatArea;
    private TextField messageField;
    private final Timeline updateTimeline;

    public ChatTab(Invoker serverCommunicator, String username, String password, Stage primaryStage, ResourceBundle bundle) {
        this.serverCommunicator = serverCommunicator;
        this.username = username;
        this.password = password;
        this.stage = primaryStage;
        this.bundle = bundle;

        this.setText(bundle.getString("chatTabTitle"));

        VBox vbox = createVBox();
        addUIControls(vbox);

        this.setContent(vbox);

        // Initialize the timeline for periodic updates
        updateTimeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> receive()));
        updateTimeline.setCycleCount(Timeline.INDEFINITE);
        updateTimeline.play();
    }

    private VBox createVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        return vbox;
    }

    private void addUIControls(VBox vbox) {
        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setPrefHeight(400);
        vbox.getChildren().add(chatArea);

        messageField = new TextField();
        messageField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendReceive();
            }
        });

        Button sendButton = new Button(bundle.getString("sendButton"));
        sendButton.setOnAction(e -> sendReceive());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.add(messageField, 0, 0);
        grid.add(sendButton, 1, 0);

        vbox.getChildren().add(grid);
    }


    private void sendReceive() {
        // Message to send to the server
        String message = username + ": " + messageField.getText().trim() + '\n';

        // Execute the chat command on the server
        String result = serverCommunicator.executeCommand(
                "chat",
                new Argument(message, serverCommunicator, username, password, stage, bundle)
        ).getResult();

        // Update the chat area with the result
        chatArea.setText(result);

        // Clear the message field
        messageField.clear();
    }
    private void receive() {
        // Execute the chat command on the server
        String result = serverCommunicator.executeCommand(
                "chat",
                new Argument("", serverCommunicator, username, password, stage, bundle)
        ).getResult();

        // Update the chat area with the result
        chatArea.setText(result + "\n");
    }
}

