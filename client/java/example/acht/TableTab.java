package example.acht;

import example.managers.Argument;
import example.managers.Invoker;
import example.objects.AstartesCategory;
import example.objects.MeleeWeapon;
import example.objects.SpaceMarine;
import example.objects.Weapon;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import static example.acht.Update.getRelevantSpaceMarines;

public class TableTab extends Tab {

    private final Invoker invoker;
    private final String username;
    private final String password;
    private final Stage stage;
    TableView<SpaceMarine> tableView;
    private ResourceBundle bundle;

    public TableTab(Invoker invoker, String username, String password, Stage stage, ResourceBundle bundle) {
        super(bundle.getString("tableTabTitle"));
        this.bundle = bundle;
        this.invoker = invoker;
        this.username = username;
        this.password = password;
        this.stage = stage;
        setContent(createTableView());
    }

    private TableView<SpaceMarine> createTableView() {
        tableView = new TableView<>();

        TableColumn<SpaceMarine, Void> actionColumn = new TableColumn<>("");
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button updateButton = new Button(bundle.getString("updateButton"));

            {
                updateButton.setOnAction(event -> {
                    SpaceMarine spaceMarine = getTableView().getItems().get(getIndex());
                    String arg = spaceMarine.getId() + "," + spaceMarine.getName() + "," + spaceMarine.getHealth() + "," + spaceMarine.getX() + "," + spaceMarine.getY() + "," + spaceMarine.getCategory() + "," + spaceMarine.getWeaponType() + "," + spaceMarine.getMeleeWeapon() + "," + spaceMarine.getChapterName() + "," + spaceMarine.getMarinesCount();
                    invoker.executeCommand("update", new Argument(arg, invoker, username, password, stage, bundle));
                    updateContent();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    SpaceMarine spaceMarine = getTableView().getItems().get(getIndex());
                    // Если имя создателя соответствует имени пользователя, добавляем кнопку
                    if (spaceMarine.getCreator().equals(username)) {
                        setGraphic(updateButton);
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });

        TableColumn<SpaceMarine, Integer> IDColumn = new TableColumn<>("ID");
        TableColumn<SpaceMarine, ZonedDateTime> creationDateColumn = new TableColumn<>(bundle.getString("spaceMarine.creationDate"));
        TableColumn<SpaceMarine, String> nameColumn = new TableColumn<>(bundle.getString("spaceMarine.name"));
        TableColumn<SpaceMarine, Float> healthColumn = new TableColumn<>(bundle.getString("spaceMarine.health"));
        TableColumn<SpaceMarine, Double> xColumn = new TableColumn<>("X");
        TableColumn<SpaceMarine, Float> yColumn = new TableColumn<>("Y");
        TableColumn<SpaceMarine, AstartesCategory> categoryColumn = new TableColumn<>(bundle.getString("spaceMarine.category"));
        TableColumn<SpaceMarine, Weapon> weaponTypeColumn = new TableColumn<>(bundle.getString("spaceMarine.weapon"));
        TableColumn<SpaceMarine, MeleeWeapon> meleeWeaponColumn = new TableColumn<>(bundle.getString("spaceMarine.meleeWeapon"));
        TableColumn<SpaceMarine, String> chapterNameColumn = new TableColumn<>(bundle.getString("spaceMarine.chapterName"));
        TableColumn<SpaceMarine, Long> marinesCountColumn = new TableColumn<>(bundle.getString("spaceMarine.marinesCount"));
        TableColumn<SpaceMarine, String> creatorColumn = new TableColumn<>(bundle.getString("spaceMarine.creator"));

        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        creationDateColumn.setCellValueFactory(cellData -> {
            ZonedDateTime creationDate = cellData.getValue().getCreationDate();
            return new SimpleObjectProperty<>(creationDate);
        });

        creationDateColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(ZonedDateTime creationDate, boolean empty) {
                super.updateItem(creationDate, empty);
                if (empty || creationDate == null) {
                    setText(null);
                } else {
                    Locale locale = bundle.getLocale();
                    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                            .withLocale(locale);
                    String formattedDate = creationDate.format(formatter);
                    setText(formattedDate);
                }
            }
        });

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        healthColumn.setCellValueFactory(new PropertyValueFactory<>("health"));
        xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        yColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        weaponTypeColumn.setCellValueFactory(new PropertyValueFactory<>("weaponType"));
        meleeWeaponColumn.setCellValueFactory(new PropertyValueFactory<>("meleeWeapon"));
        chapterNameColumn.setCellValueFactory(new PropertyValueFactory<>("chapterName"));
        marinesCountColumn.setCellValueFactory(new PropertyValueFactory<>("marinesCount"));
        creatorColumn.setCellValueFactory(new PropertyValueFactory<>("creator"));

        tableView.getColumns().add(IDColumn);
        tableView.getColumns().add(creationDateColumn);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(healthColumn);
        tableView.getColumns().add(xColumn);
        tableView.getColumns().add(yColumn);
        tableView.getColumns().add(categoryColumn);
        tableView.getColumns().add(weaponTypeColumn);
        tableView.getColumns().add(meleeWeaponColumn);
        tableView.getColumns().add(chapterNameColumn);
        tableView.getColumns().add(marinesCountColumn);
        tableView.getColumns().add(creatorColumn);
        tableView.getColumns().add(actionColumn);

        updateContent();

        return tableView;
    }

    public void updateContent() {
        ArrayList<SpaceMarine> spaceMarines = getRelevantSpaceMarines(invoker, username, password, stage);

        ObservableList<SpaceMarine> tableData = FXCollections.observableArrayList(spaceMarines);
        tableView.setItems(tableData);
    }
}