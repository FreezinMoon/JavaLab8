package example;

import example.acht.*;
import example.managers.Argument;
import example.managers.Invoker;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Main extends Application {

    private static String username;
    private static String password;
    private ResourceBundle bundle;
    private Stage primaryStage;
    private Invoker serverCommunicator;
    private Label messageLabel;
    private Label usernameLabel;
    private Label passwordLabel;
    private Button loginButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.serverCommunicator = new Invoker();
        bundle = new Language().setLanguage("English"); // Set the default language

        primaryStage.setTitle(bundle.getString("authorizationTitle"));

        GridPane grid = createGridPane();
        addUIControls(grid);

        Scene scene = new Scene(grid, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createGridPane() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);
        return grid;
    }

    private void addUIControls(GridPane grid) {
        usernameLabel = new Label(bundle.getString("usernameLabel"));
        TextField usernameField = new TextField();
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);

        passwordLabel = new Label(bundle.getString("passwordLabel"));
        PasswordField passwordField = new PasswordField();
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);

        loginButton = new Button(bundle.getString("loginButton"));
        loginButton.setOnAction(e -> {
            username = usernameField.getText();
            password = passwordField.getText();
            sendAuthenticationRequest();
        });
        grid.add(loginButton, 1, 2);

        messageLabel = new Label();
        grid.add(messageLabel, 0, 3, 2, 1);

        ComboBox<String> languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll(
                "English",
                "Russian",
                "Macedonian",
                "Croatian"
        );
        languageComboBox.setPromptText("Language");
        languageComboBox.setOnAction(e -> {
            String selectedLanguage = languageComboBox.getValue();
            if (selectedLanguage != null) {
                bundle = new Language().setLanguage(selectedLanguage);
                updateUIControls();
            }
        });
        grid.add(languageComboBox, 0, 2);
    }

    private void updateUIControls() {
        primaryStage.setTitle(bundle.getString("authorizationTitle"));
        usernameLabel.setText(bundle.getString("usernameLabel"));
        passwordLabel.setText(bundle.getString("passwordLabel"));
        loginButton.setText(bundle.getString("loginButton"));
        messageLabel.setText("");
    }

    private void sendAuthenticationRequest() {
        boolean isAuthenticated = serverCommunicator.executeCommand("authentication", new Argument("", serverCommunicator, username, password, primaryStage, bundle)).isSuccess();

        if (isAuthenticated) {
            System.out.println(bundle.getString("loginSuccessful"));
            primaryStage.close();
            launchTabbedInterface();
        } else {
            System.out.println(bundle.getString("invalidCredentials"));
            messageLabel.setText(bundle.getString("incorrectUsernamePassword"));
        }
    }

    private void launchTabbedInterface() {
        primaryStage.setTitle(String.format(bundle.getString("spaceMarinesTitle"), username));

        // Create tabs
        TabPane tabPane = new TabPane();
        Tab coordinateGridTab = new CoordinateGridTab(serverCommunicator, username, password, primaryStage, bundle);
        Tab tableTab = new TableTab(serverCommunicator, username, password, primaryStage, bundle);
        Tab terminalTab = new TerminalTab(serverCommunicator, username, password, primaryStage, bundle);
        Tab chatTab = new ChatTab(serverCommunicator, username, password, primaryStage, bundle);

        // Add tabs to the tab pane
        tabPane.getTabs().addAll(tableTab, coordinateGridTab, terminalTab, chatTab);

        // Add event handler to update the tab content when selected
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab == tableTab) {
                // Update table tab content
                ((TableTab) newTab).updateContent();
            } else if (newTab == coordinateGridTab) {
                // Update coordinate grid tab content
                ((CoordinateGridTab) newTab).updateContent(bundle);
            }
        });

        // Create the main scene
        Scene scene = new Scene(tabPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}