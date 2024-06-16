package ralf.vanaert.nerdingtontournament.Controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import ralf.vanaert.nerdingtontournament.Model.Player;

import java.net.URL;
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
    public Button addRound;

    private ObservableList<Player> players;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        players = FXCollections.observableArrayList();

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(event -> event.getRowValue().setName(event.getNewValue()));

        score.setCellValueFactory(cellData -> cellData.getValue().getScoreProperty());

        table.setItems(players);
        table.setEditable(true);

        addPlayer.setOnAction(event -> addPlayer());
        addRound.setOnAction(event -> addRound());
    }

    private void addRound() {
        this.roundCount++;
        this.addResultColumn(this.roundCount - 1);

    }

    private void addPlayer() {
        Player player = new Player();
        players.add(player);
    }

    private void addResultColumn(int index) {
        TableColumn<Player, Integer> resultColumn = new TableColumn<>("Round " + (index + 1));
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
