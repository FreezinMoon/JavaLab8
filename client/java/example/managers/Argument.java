package example.managers;

import javafx.stage.Stage;

import java.util.ResourceBundle;

public record Argument(String arg, Invoker invoker, String login, String password, Stage stage, ResourceBundle bundle) {

}