package ralf.vanaert.nerdingtontournament.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.util.StringConverter;
import ralf.vanaert.nerdingtontournament.Model.Player;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class ResultViewController implements Initializable {

    @FXML
    private ListView<String> listView;
    @FXML
    private LineChart<Number, Number> chart;

    public void setPlayers(List<Player> players) {
        players.sort(Comparator.comparingInt(Player::getScore).reversed());
        listView.getItems().clear();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            listView.getItems().add((i + 1) + ". " + player.getName() + " - " + player.getScore());
        }

        chart.getData().clear();
        XYChart.Series<Number, Number> series;
        for (Player player : players) {
            series = new XYChart.Series<>();
            series.setName(player.getName());
            for (int round = 0; round < player.getResultSize(); round ++) {
                int scoreAtRound = 0;

                for (int countingAt = 0; countingAt <= round; countingAt++) {
                    scoreAtRound += player.getResult(countingAt);
                }

                series.getData().add(new XYChart.Data<>(round + 1, scoreAtRound));
            }
            chart.getData().add(series);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
