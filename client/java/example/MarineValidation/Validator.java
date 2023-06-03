package example.MarineValidation;

import example.objects.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class Validator {
    public SpaceMarine createSpaceMarine(Stage primaryStage, String login, ResourceBundle bundle) {
        // Create a new window
        Stage stage = new Stage();
        stage.setTitle(bundle.getString("createSpaceMarineTitle"));

        // Create the labels and text fields for input
        Label nameLabel = new Label(bundle.getString("spaceMarine.name") + ":");
        TextField nameTextField = new TextField();

        Label healthLabel = new Label(bundle.getString("spaceMarine.health") + ":");
        TextField healthTextField = new TextField();

        Label xLabel = new Label(bundle.getString("spaceMarine.x") + ":");
        TextField xTextField = new TextField();

        Label yLabel = new Label(bundle.getString("spaceMarine.y") + ":");
        TextField yTextField = new TextField();

        Label astartesLabel = new Label(bundle.getString("spaceMarine.category") + ":");
        ChoiceBox<AstartesCategory> astartesCategoryChoiceBox = new ChoiceBox<>();
        astartesCategoryChoiceBox.getItems().addAll(AstartesCategory.values());
        astartesCategoryChoiceBox.setValue(AstartesCategory.ASSAULT);


        Label weaponLabel = new Label(bundle.getString("spaceMarine.weapon") + ":");
        ChoiceBox<Weapon> weaponChoiceBox = new ChoiceBox<>();
        weaponChoiceBox.getItems().addAll(Weapon.values());
        weaponChoiceBox.setValue(Weapon.BOLTGUN);


        Label meleeWeaponLabel = new Label(bundle.getString("spaceMarine.meleeWeapon") + ":");
        ChoiceBox<MeleeWeapon> meleeWeaponChoiceBox = new ChoiceBox<>();
        meleeWeaponChoiceBox.getItems().addAll(MeleeWeapon.values());
        meleeWeaponChoiceBox.setValue(MeleeWeapon.LIGHTING_CLAW);


        Label chNameLabel = new Label(bundle.getString("spaceMarine.chapterName") + ":");
        TextField chNameTextField = new TextField();

        Label countLabel = new Label(bundle.getString("spaceMarine.marinesCount") + ":");
        TextField countTextField = new TextField();

        // Create the OK button
        Button okButton = new Button("OK");
        AtomicReference<SpaceMarine> spaceMarine = new AtomicReference<>();
        okButton.setOnAction(event -> {
            // Retrieve the input values from the text fields
            String name;
            float health;
            double x;
            Float y;
            AstartesCategory category;
            Weapon weaponType;
            MeleeWeapon meleeWeapon;
            String chapterName;
            Long marinesCount;

            try {
                name = nameTextField.getText();
                health = Float.parseFloat(healthTextField.getText());
                x = Double.parseDouble(xTextField.getText());
                y = Float.parseFloat(yTextField.getText());
                category = astartesCategoryChoiceBox.getValue();
                weaponType = weaponChoiceBox.getValue();
                meleeWeapon = meleeWeaponChoiceBox.getValue();
                chapterName = chNameTextField.getText();
                marinesCount = Long.parseLong(countTextField.getText());


                // Validate that none of the fields are null
                if (name.isEmpty() || chapterName.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(bundle.getString("invalidInput"));
                    alert.setHeaderText(null);
                    alert.setContentText(bundle.getString("invalidInputContent"));
                    alert.showAndWait();
                    return;
                }
                if (health <= 0 || x <= -892 || marinesCount <= 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(bundle.getString("invalidInput"));
                    alert.setHeaderText(null);
                    alert.setContentText(bundle.getString("invalidInputContent"));
                    alert.showAndWait();
                    return;
                }

                // Create a new SpaceMarine instance with the input values
                spaceMarine.set(new SpaceMarine(name, health, new Coordinates(x, y), category, weaponType, meleeWeapon, new Chapter(marinesCount, chapterName), login));

                // Close the window
                stage.close();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(bundle.getString("invalidInput"));
                alert.setHeaderText(null);
                alert.setContentText(bundle.getString("invalidInputContent"));
                alert.showAndWait();
            } catch (Exception e) {
                // Handle other validation errors
                System.out.println(bundle.getString("invalidInput") + ": " + e.getMessage());
            }
        });

        // Create a grid pane to arrange the labels, text fields, and button
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add the labels, text fields, and button to the grid pane
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameTextField, 1, 0);

        gridPane.add(healthLabel, 0, 1);
        gridPane.add(healthTextField, 1, 1);

        gridPane.add(xLabel, 0, 2);
        gridPane.add(xTextField, 1, 2);

        gridPane.add(yLabel, 0, 3);
        gridPane.add(yTextField, 1, 3);

        gridPane.add(astartesLabel, 0, 4);
        gridPane.add(astartesCategoryChoiceBox, 1, 4);

        gridPane.add(weaponLabel, 0, 5);
        gridPane.add(weaponChoiceBox, 1, 5);

        gridPane.add(meleeWeaponLabel, 0, 6);
        gridPane.add(meleeWeaponChoiceBox, 1, 6);

        gridPane.add(chNameLabel, 0, 7);
        gridPane.add(chNameTextField, 1, 7);

        gridPane.add(countLabel, 0, 8);
        gridPane.add(countTextField, 1, 8);

        gridPane.add(okButton, 0, 9, 2, 1);


        // Create the scene and set it to the stage
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);

        // Show the window and wait for it to be closed
        stage.initOwner(primaryStage);
        stage.showAndWait();

        // Return the created SpaceMarine instance
        return spaceMarine.get();
    }
    public SpaceMarine updateSpaceMarine(Stage primaryStage, String login, String argument, ResourceBundle bundle) {
        // Create a new window
        Stage stage = new Stage();
        stage.setTitle(bundle.getString("updateSpaceMarineTitle"));

        String[] args = argument.split(",");

        // Create the labels and text fields for input
        Label nameLabel = new Label(bundle.getString("spaceMarine.name") + ":");
        TextField nameTextField = new TextField(args[1]);

        Label healthLabel = new Label(bundle.getString("spaceMarine.health") + ":");
        TextField healthTextField = new TextField(String.valueOf(args[2]));

        Label xLabel = new Label(bundle.getString("spaceMarine.x") + ":");
        TextField xTextField = new TextField(String.valueOf(args[3]));

        Label yLabel = new Label(bundle.getString("spaceMarine.y") + ":");
        TextField yTextField = new TextField(String.valueOf(args[4]));

        Label astartesLabel = new Label(bundle.getString("spaceMarine.category") + ":");
        ChoiceBox<AstartesCategory> astartesCategoryChoiceBox = new ChoiceBox<>();
        astartesCategoryChoiceBox.getItems().addAll(AstartesCategory.values());
        astartesCategoryChoiceBox.setValue(AstartesCategory.valueOf(args[5]));

        Label weaponLabel = new Label(bundle.getString("spaceMarine.weapon") + ":");
        ChoiceBox<Weapon> weaponChoiceBox = new ChoiceBox<>();
        weaponChoiceBox.getItems().addAll(Weapon.values());
        weaponChoiceBox.setValue(Weapon.valueOf(args[6]));

        Label meleeWeaponLabel = new Label(bundle.getString("spaceMarine.meleeWeapon") + ":");
        ChoiceBox<MeleeWeapon> meleeWeaponChoiceBox = new ChoiceBox<>();
        meleeWeaponChoiceBox.getItems().addAll(MeleeWeapon.values());
        meleeWeaponChoiceBox.setValue(MeleeWeapon.valueOf(args[7]));

        Label chNameLabel = new Label(bundle.getString("spaceMarine.chapterName") + ":");
        TextField chNameTextField = new TextField(args[8]);

        Label countLabel = new Label(bundle.getString("spaceMarine.marinesCount") + ":");
        TextField countTextField = new TextField(String.valueOf(args[9]));

        // Create the OK button
        Button okButton = new Button("OK");
        AtomicReference<SpaceMarine> updatedSpaceMarine = new AtomicReference<>();
        okButton.setOnAction(event -> {
            // Retrieve the input values from the text fields
            String name;
            float health;
            double x;
            Float y;
            AstartesCategory category;
            Weapon weaponType;
            MeleeWeapon meleeWeapon;
            String chapterName;
            Long marinesCount;

            try {
                name = nameTextField.getText();
                health = Float.parseFloat(healthTextField.getText());
                x = Double.parseDouble(xTextField.getText());
                y = Float.parseFloat(yTextField.getText());
                category = astartesCategoryChoiceBox.getValue();
                weaponType = weaponChoiceBox.getValue();
                meleeWeapon = meleeWeaponChoiceBox.getValue();
                chapterName = chNameTextField.getText();
                marinesCount = Long.parseLong(countTextField.getText());

                // Validate that none of the fields are null
                if (name.isEmpty() || chapterName.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(bundle.getString("invalidInput"));
                    alert.setHeaderText(null);
                    alert.setContentText(bundle.getString("invalidInputContent"));
                    alert.showAndWait();
                    return;
                }
                if (health <= 0 || x <= -892 || marinesCount <= 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(bundle.getString("invalidInput"));
                    alert.setHeaderText(null);
                    alert.setContentText(bundle.getString("invalidInputContent"));
                    alert.showAndWait();
                    return;
                }

                // Create a new SpaceMarine instance with the input values
                updatedSpaceMarine.set(new SpaceMarine(name, health, new Coordinates(x, y), category, weaponType, meleeWeapon, new Chapter(marinesCount, chapterName), login));

                // Close the window
                stage.close();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(bundle.getString("invalidInput"));
                alert.setHeaderText(null);
                alert.setContentText(bundle.getString("invalidInputContent"));
                alert.showAndWait();
            } catch (Exception e) {
                // Handle other validation errors
                System.out.println("Invalid input: " + e.getMessage());
            }
        });

        // Create a grid pane to arrange the labels, text fields, and button
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add the labels, text fields, and button to the grid pane
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameTextField, 1, 0);

        gridPane.add(healthLabel, 0, 1);
        gridPane.add(healthTextField, 1, 1);

        gridPane.add(xLabel, 0, 2);
        gridPane.add(xTextField, 1, 2);

        gridPane.add(yLabel, 0, 3);
        gridPane.add(yTextField, 1, 3);

        gridPane.add(astartesLabel, 0, 4);
        gridPane.add(astartesCategoryChoiceBox, 1, 4);

        gridPane.add(weaponLabel, 0, 5);
        gridPane.add(weaponChoiceBox, 1, 5);

        gridPane.add(meleeWeaponLabel, 0, 6);
        gridPane.add(meleeWeaponChoiceBox, 1, 6);

        gridPane.add(chNameLabel, 0, 7);
        gridPane.add(chNameTextField, 1, 7);

        gridPane.add(countLabel, 0, 8);
        gridPane.add(countTextField, 1, 8);

        gridPane.add(okButton, 0, 9, 2, 1);

        // Create the scene and set it to the stage
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);

        // Show the window and wait for it to be closed
        stage.initOwner(primaryStage);
        stage.showAndWait();

        // Return the updated SpaceMarine instance
        return updatedSpaceMarine.get();
    }

}
