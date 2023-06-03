package example.acht;

import example.managers.Argument;
import example.managers.Invoker;
import example.managers.Product;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class TerminalTab extends Tab {
    private final Invoker serverCommunicator;
    private final String username;
    private final String password;
    private final Stage stage;
    private ResourceBundle bundle;

    public TerminalTab(Invoker serverCommunicator, String username, String password, Stage stage, ResourceBundle bundle) {
        super(bundle.getString("terminalTabTitle"));
        this.bundle = bundle;
        this.serverCommunicator = serverCommunicator;
        this.username = username;
        this.password = password;
        this.stage = stage;
        setContent(createTerminalTextArea());
    }

    private TextArea createTerminalTextArea() {
        TextArea terminalTextArea = new TextArea();
        terminalTextArea.setEditable(true);

        // Create a StringBuilder to store the command history and results
        StringBuilder commandHistory = new StringBuilder();

        // Define the input prompt
        String inputPrompt = "> ";

        // Capture key events to simulate a terminal-like repeater
        terminalTextArea.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String[] preLine = terminalTextArea.getText().split("> ");
                String[] line = preLine[preLine.length - 1].trim().split(" ");

                // Determine the command and argument to execute
                String arg = line.length > 1 ? line[1] : "";
                // Handle the command as needed
                String commandOutput = processCommand(line[0], arg, terminalTextArea);

                commandHistory.append("> ").append(preLine[preLine.length - 1]).append(System.lineSeparator());
                commandHistory.append(commandOutput).append(System.lineSeparator());

                // Update the text area with the command history and input prompt
                terminalTextArea.setText(commandHistory + inputPrompt);

                // Move the caret to the end of the text area
                terminalTextArea.positionCaret(terminalTextArea.getLength());

                // Prevent adding a new line after the command output
                event.consume();
            }
        });

        // Set the initial input prompt
        terminalTextArea.setText(inputPrompt);

        return terminalTextArea;
    }

    private String processCommand(String command, String arg, TextArea terminalTextArea) {
        Argument argument = new Argument(arg, serverCommunicator, username, password, stage, bundle);

        Product product = serverCommunicator.executeCommand(command, argument);
        terminalTextArea.appendText(product.getResult() + System.lineSeparator());
        return product.getResult();
    }
}
