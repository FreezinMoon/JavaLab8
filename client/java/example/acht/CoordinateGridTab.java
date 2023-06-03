package example.acht;

import example.managers.Argument;
import example.managers.Invoker;
import example.objects.SpaceMarine;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static example.acht.Update.getRelevantSpaceMarines;

public class CoordinateGridTab extends Tab {
    private final Invoker invoker;
    private ResourceBundle bundle;
    private final String username;
    private final String password;
    private final Stage stage;
    private ComboBox<String> languageComboBox;

    public CoordinateGridTab(Invoker invoker, String username, String password, Stage stage, ResourceBundle bundle) {
        super(bundle.getString("coordinateGridTabTitle"));
        this.bundle = bundle;
        this.invoker = invoker;
        this.username = username;
        this.password = password;
        this.stage = stage;
        updateContent(bundle);
    }

    public void updateContent(ResourceBundle bundle1) {
        this.bundle = bundle1;
        GridPane gridPane = new GridPane();

        Canvas canvas = new Canvas(800, 600);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        gridPane.getChildren().add(canvas);
        // Clear the canvas
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        setContent(gridPane);

        // Get the updated list of space marines
        List<SpaceMarine> spaceMarines = getRelevantSpaceMarines(invoker, username, password, stage);

        // Draw and animate the space marines
        for (SpaceMarine spaceMarine : spaceMarines) {
            double x = spaceMarine.getX();
            double y = spaceMarine.getY();
            String owner = spaceMarine.getCreator();

            // Choose color based on the owner
            Color color = getColorForOwner(owner);

            visualizeSpaceMarine(graphicsContext, x, y, color);
        }

        // Add click event handler
        canvas.setOnMouseClicked(event -> {
            for (SpaceMarine spaceMarine : spaceMarines) {
                if (isClickedOnSpaceMarine(spaceMarine, event.getX(), event.getY())) {
                    displaySpaceMarineInfo(spaceMarine);
                    break; // Stop checking after finding the clicked space marine
                }
            }
        });

        // Create language switcher
        languageComboBox = new ComboBox<>();
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
                this.bundle = new Language().setLanguage(selectedLanguage);
                updateContent(new Language().setLanguage(selectedLanguage));
            }
        });
        // gridPane.add(languageComboBox, 0, 0);
    }

    private Color getColorForOwner(String owner) {
        // Calculate the hash value of the owner string
        int hash = owner.hashCode();

        // Extract the individual RGB components from the hash value
        int red = (hash & 0xFF0000) >> 16;
        int green = (hash & 0x00FF00) >> 8;
        int blue = hash & 0x0000FF;

        // Normalize the RGB values to the range [0, 1]
        double normalizedRed = red / 255.0;
        double normalizedGreen = green / 255.0;
        double normalizedBlue = blue / 255.0;

        // Create and return the color based on the normalized RGB values
        return new Color(normalizedRed, normalizedGreen, normalizedBlue, 1.0);
    }

    private boolean isClickedOnSpaceMarine(SpaceMarine spaceMarine, double clickX, double clickY) {
        double x = spaceMarine.getX();
        double y = spaceMarine.getY();
        double spaceMarineSize = 40; // Assuming the spaceMarine size is 40
        double halfSize = spaceMarineSize / 2;

        // Check if the click position is within the boundaries of the space marine
        boolean withinX = clickX >= (x - halfSize) && clickX <= (x + halfSize);
        boolean withinY = clickY >= (y - halfSize) && clickY <= (y + halfSize);

        // Return true if clicked on the space marine, false otherwise
        return withinX && withinY;
    }

    private void displaySpaceMarineInfo(SpaceMarine spaceMarine) {
        String info = String.format("""
                        ID: %d
                        %s: %s
                        %s: %s
                        %s: %.2f
                        %s: (%.2f, %.2f)
                        %s: %s
                        %s: %s
                        %s: %s
                        %s: (%s, %d)
                        %s: %s""",
                spaceMarine.getId(),
                bundle.getString("spaceMarine.creationDate"),
                spaceMarine.getCreationDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                        .withLocale(bundle.getLocale())),
                bundle.getString("spaceMarine.name"),
                spaceMarine.getName(),
                bundle.getString("spaceMarine.health"),
                spaceMarine.getHealth(),
                bundle.getString("spaceMarine.position"),
                spaceMarine.getX(),
                spaceMarine.getY(),
                bundle.getString("spaceMarine.category"),
                spaceMarine.getCategory(),
                bundle.getString("spaceMarine.weapon"),
                spaceMarine.getWeaponType(),
                bundle.getString("spaceMarine.meleeWeapon"),
                spaceMarine.getMeleeWeapon(),
                bundle.getString("spaceMarine.chapter"),
                spaceMarine.getChapterName(),
                spaceMarine.getMarinesCount(),
                bundle.getString("spaceMarine.creator"),
                spaceMarine.getCreator()
        );
        // Create an alert dialog box with the information
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("spaceMarine.infoTitle"));
        alert.setHeaderText(null);
        alert.setContentText(info);

        if (spaceMarine.getCreator().equals(username)) {
            // Create buttons for update and delete
            ButtonType updateButton = new ButtonType(bundle.getString("updateButton"));
            ButtonType deleteButton = new ButtonType(bundle.getString("deleteButton"));

            // Set the buttons in the alert dialog
            alert.getButtonTypes().setAll(updateButton, deleteButton, ButtonType.OK);

            // Wait for the user's response
            Optional<ButtonType> result = alert.showAndWait();

            // Handle the button clicks
            if (result.isPresent()) {
                if (result.get() == updateButton) {
                    // Handle update button click
                    updateSpaceMarine(spaceMarine);
                } else if (result.get() == deleteButton) {
                    // Handle delete button click
                    deleteSpaceMarine(spaceMarine);
                }
            }
        } else {
            // Set the OK button in the alert dialog
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void updateSpaceMarine(SpaceMarine spaceMarine) {
        String arg = spaceMarine.getId() + "," + spaceMarine.getName() + "," + spaceMarine.getHealth() + "," + spaceMarine.getX() + "," + spaceMarine.getY() + "," + spaceMarine.getCategory() + "," + spaceMarine.getWeaponType() + "," + spaceMarine.getMeleeWeapon() + "," + spaceMarine.getChapterName() + "," + spaceMarine.getMarinesCount();
        invoker.executeCommand("update", new Argument(arg, invoker, username, password, stage, bundle));
        updateContent(bundle);
    }

    private void deleteSpaceMarine(SpaceMarine spaceMarine) {
        invoker.executeCommand("remove_by_id", new Argument(String.valueOf(spaceMarine.getId()), invoker, username, password, stage, bundle));
        updateContent(bundle);
    }

    private void visualizeSpaceMarine(GraphicsContext gc, double x, double y, Color color) {
        double spaceMarineSize = 15;

        // Draw the initial space marine
        gc.setFill(color);
        gc.fillOval(x - spaceMarineSize / 2, y - spaceMarineSize / 2, spaceMarineSize, spaceMarineSize * 2);

        // Animation properties
        long animationDuration = 1000; // 1000 milliseconds (1 second)
        double maxScale = 1.5;

        AnimationTimer animationTimer = new AnimationTimer() {
            private long startTime;

            @Override
            public void start() {
                startTime = System.currentTimeMillis();
                super.start();
            }

            @Override
            public void handle(long now) {
                long elapsedTime = System.currentTimeMillis() - startTime;

                // Calculate the scale based on the elapsed time
                double scale = 1 + (maxScale - 1) * (elapsedTime % animationDuration) / animationDuration;

                // Clear the previous frame
                gc.clearRect(x - spaceMarineSize / 2, y - spaceMarineSize / 2, spaceMarineSize, spaceMarineSize);

                // Draw the animated space marine with the new scale
                gc.save();
                gc.setFill(color);
                gc.translate(x, y);
                gc.scale(scale, scale);
                gc.fillOval(-spaceMarineSize / 2, -spaceMarineSize / 2, spaceMarineSize, spaceMarineSize);
                gc.restore();

                // Stop the animation after the specified duration
                if (elapsedTime >= animationDuration) {
                    stop();
                }
            }
        };
        animationTimer.start();
    }
}
