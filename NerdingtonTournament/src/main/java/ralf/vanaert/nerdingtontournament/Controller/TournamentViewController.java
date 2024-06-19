package ralf.vanaert.nerdingtontournament.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import ralf.vanaert.nerdingtontournament.Model.Game;
import ralf.vanaert.nerdingtontournament.Model.Player;
import ralf.vanaert.nerdingtontournament.TournamentApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TournamentViewController implements Initializable {
    private int roundCount = 0;

    @FXML
    private TableView<Player> table;

    @FXML
    private TableColumn<Player, String> name;
    @FXML
    private TableColumn<Player, Number> score;
    @FXML
    private Button addPlayer;
    @FXML
    private Button removePlayer;
    @FXML
    private Button addRound;
    @FXML
    private Button removeRound;
    @FXML
    private Button editGames;

    private ObservableList<Player> players;
    private ObservableList<Game> games;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        players = FXCollections.observableArrayList();

        int amountPlayers = 15;
        for (int i = 1; i <= amountPlayers; i++) {
            players.add(new Player("Player " + i));
        }

        games = FXCollections.observableArrayList(
                new Game("Skip-bo", 3, 6),
                new Game("Uno", 3, 6),
                new Game("Qwixx", 4, 6),
                new Game("Nope!", 3, 6),
                new Game("Hali Galli", 2, 6),
                new Game("Exploding Kittens", 3, 5),
                new Game("3D 4 op een rij", 2, 2),
                new Game("Kwartet", 3,6),
                new Game("Memory", 2,4),
                new Game("Klik!", 2, 4),
                new Game("Set", 3,6),
                new Game("Mens erger je niet!", 3,4),
                new Game("Lego Minotarus", 3, 4)
        );

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(event -> event.getRowValue().setName(event.getNewValue()));

        score.setCellValueFactory(cellData -> cellData.getValue().getScoreProperty());

        table.setItems(players);
        table.setEditable(true);

        addPlayer.setOnAction(event -> addPlayer());
        removePlayer.setOnAction(event -> removePlayer());
        addRound.setOnAction(event -> addRound());
        removeRound.setOnAction(event -> removeRound());
        editGames.setOnAction(event -> editGames());
    }

    private void addRound() {
        this.roundCount++;
        this.addResultColumn(this.roundCount - 1);
        showMatchmaking();
    }

    private void addPlayer() {
        Player player = new Player();
        players.add(player);
    }

    private void removePlayer() {
        Player player = table.getSelectionModel().getSelectedItem();
        if (player != null) {
            players.remove(player);
        }
    }

    private void removeRound() {
        if (roundCount > 0) {
            roundCount--;
            table.getColumns().remove(table.getColumns().size() - 1);
        }
    }

    private void editGames() {
        FXMLLoader fxmlLoader = new FXMLLoader(TournamentApplication.class.getResource("edit-games-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            EditGamesController controller = fxmlLoader.getController();
            controller.setGames(games);
            Stage stage = new Stage();
            stage.setTitle("Edit Games");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showMatchmaking() {
        FXMLLoader fxmlLoader = new FXMLLoader(TournamentApplication.class.getResource("matchmaking-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            MatchmakingViewController controller = fxmlLoader.getController();
            controller.initMatchmaking(players, games);
            Stage stage = new Stage();
            stage.setTitle("Nerdington Tournament");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void addResultColumn(int index) {
        TableColumn<Player, Integer> resultColumn = new TableColumn<>("R. " + (index + 1));
        resultColumn.setCellValueFactory(cellData -> cellData.getValue().getResultProperty(index).asObject());
        resultColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            @Override
            public String toString(Integer object) {
                return object == null ? "" : object.toString();
            }

            @Override
            public Integer fromString(String string) {
                return string.isEmpty() ? null : Integer.parseInt(string);
            }
        }));
        resultColumn.setOnEditCommit(event -> {
            event.getRowValue().setResult(index, event.getNewValue());
        });
        table.getColumns().add(resultColumn);
    }
}
