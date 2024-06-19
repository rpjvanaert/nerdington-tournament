package ralf.vanaert.nerdingtontournament.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import ralf.vanaert.nerdingtontournament.Model.Game;

public class EditGamesController {
    @FXML
    private TableView<Game> gameTable;
    @FXML
    private TableColumn<Game, String> gameName;
    @FXML
    private TableColumn<Game, Integer> minPlayers;
    @FXML
    private TableColumn<Game, Integer> maxPlayers;
    @FXML
    private Button addGame;
    @FXML
    private Button removeGame;
    @FXML
    private Button saveGames;

    private ObservableList<Game> games;

    public void initialize() {
        games = FXCollections.observableArrayList(
                new Game("Skip-bo", 3, 6),
                new Game("Uno", 3, 6),
                new Game("Qwixx", 4, 6),
                new Game("Nope!", 3, 6),
                new Game("Hali Galli", 2, 6),
                new Game("Exploding Kittens", 3, 5),
                new Game("3D 4 op een rij", 2, 2),
                new Game("Kwartet", 3, 6),
                new Game("Memory", 2, 4),
                new Game("Klik!", 2, 4),
                new Game("Set", 3, 6),
                new Game("Mens erger je niet!", 3, 4),
                new Game("Lego Minotarus", 3, 4)
        );

        gameTable.setEditable(true);
        gameName.setCellValueFactory(new PropertyValueFactory<>("name"));
        minPlayers.setCellValueFactory(new PropertyValueFactory<>("minPlayers"));
        maxPlayers.setCellValueFactory(new PropertyValueFactory<>("maxPlayers"));

        gameName.setCellFactory(TextFieldTableCell.forTableColumn());
        minPlayers.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            @Override
            public String toString(Integer object) {
                return object == null ? "" : object.toString();
            }

            @Override
            public Integer fromString(String string) {
                return string.isEmpty() ? null : Integer.parseInt(string);
            }
        }));
        maxPlayers.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            @Override
            public String toString(Integer object) {
                return object == null ? "" : object.toString();
            }

            @Override
            public Integer fromString(String string) {
                return string.isEmpty() ? null : Integer.parseInt(string);
            }
        }));

        gameTable.setItems(games);

        addGame.setOnAction(event -> addGame());
        removeGame.setOnAction(event -> removeGame());
        saveGames.setOnAction(event -> saveGames());
    }

    private void addGame() {
        games.add(new Game("New Game", 2, 4));
    }

    private void removeGame() {
        Game selectedGame = gameTable.getSelectionModel().getSelectedItem();
        if (selectedGame != null) {
            games.remove(selectedGame);
        }
    }

    private void saveGames() {
        // Logic to save games to the main application (e.g., pass games back to the main controller)
        Stage stage = (Stage) saveGames.getScene().getWindow();
        stage.close();
    }

    public ObservableList<Game> getGames() {
        return games;
    }

    public void setGames(ObservableList<Game> games) {
        this.games = games;
        gameTable.setItems(games);
    }
}
