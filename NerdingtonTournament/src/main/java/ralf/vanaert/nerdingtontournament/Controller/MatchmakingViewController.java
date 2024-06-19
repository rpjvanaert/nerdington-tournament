package ralf.vanaert.nerdingtontournament.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ralf.vanaert.nerdingtontournament.Model.AssignedGame;
import ralf.vanaert.nerdingtontournament.Model.Game;
import ralf.vanaert.nerdingtontournament.Model.Player;

import java.net.URL;
import java.util.*;

import static ralf.vanaert.nerdingtontournament.StylingPresets.FONT_HEADER;
import static ralf.vanaert.nerdingtontournament.StylingPresets.FONT_TEXT;

public class MatchmakingViewController implements Initializable {

    @FXML
    private VBox pairings;

    private ObservableList<AssignedGame> assignedGames;

    public void initMatchmaking(ObservableList<Player> players, ObservableList<Game> games) {
        FXCollections.sort(players, Comparator.comparingInt(Player::getScore).reversed());
        List<Player> unassignedPlayers = new ArrayList<>(players);
        List<Game> matchmakingGames = new ArrayList<>(games);
        while (!unassignedPlayers.isEmpty()) {
            this.assignedGames.add(pickRandom(matchmakingGames, unassignedPlayers));
        }
    }

    private AssignedGame pickRandom(List<Game> games, List<Player> unassignedPlayers) {
        AssignedGame game = null;

        List<Integer> possibleSizes = determinePossibleSizes(unassignedPlayers);

        if (possibleSizes.isEmpty()) {
            // Handle the case where no valid sizes are available
            // You can throw an exception, log an error, or handle it based on your application logic
            throw new IllegalArgumentException("No valid sizes available for matchmaking.");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(possibleSizes.size());
        int amountPlayers = possibleSizes.get(randomIndex);

        List<Player> assignedPlayers = new ArrayList<>();
        for (int i = 0; i < amountPlayers; i++) {
            assignedPlayers.add(unassignedPlayers.remove(0));
        }

        List<Game> possibleGames = new ArrayList<>();
        for (Game eachGame : games) {
            if (amountPlayers >= eachGame.getMinPlayers() && amountPlayers <= eachGame.getMaxPlayers()) {
                possibleGames.add(eachGame);
            }
        }

        if (possibleGames.isEmpty()) {
            // Handle the case where no valid games are available for the selected size
            // You can throw an exception, log an error, or handle it based on your application logic
            throw new IllegalArgumentException("No valid games available for matchmaking with " + amountPlayers + " players.");
        }

        randomIndex = random.nextInt(possibleGames.size());
        game = new AssignedGame(possibleGames.get(randomIndex), assignedPlayers);
        games.remove(possibleGames.get(randomIndex));

        return game;
    }


    private static List<Integer> determinePossibleSizes(List<Player> unassignedPlayers) {
        List<Integer> possibleSizes = new ArrayList<>();
        int playersLeft = unassignedPlayers.size();

        for (int size = 2; size <= 6; size++)
            if (size == playersLeft || playersLeft - size >= 2) possibleSizes.add(size);
        return possibleSizes;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.assignedGames = FXCollections.observableArrayList();
        assignedGames.addListener((ListChangeListener<AssignedGame>) change -> {
            pairings.getChildren().clear();
            for (AssignedGame assignedGame : assignedGames) {
                pairings.getChildren().add(getMatchmakingCard(assignedGame));
            }
        });
    }

    private static Pane getMatchmakingCard(AssignedGame assignedGame) {
        VBox container = new VBox();
        container.setPadding(new Insets(0, 0, 20, 0));
        container.setSpacing(10);
        Label gameLabel = new Label(assignedGame.game().getName());
        gameLabel.setFont(FONT_HEADER);
        container.getChildren().add(gameLabel);
        container.getChildren().addAll(getPlayerLabels(assignedGame.players()));
        return container;
    }

    private static List<Label> getPlayerLabels(List<Player> players) {
        List<Label> labels = new ArrayList<>();

        players.forEach(player -> {
            Label label = new Label("- " + player.getName());
            label.setFont(FONT_TEXT);
            labels.add(label);
        });

        return labels;
    }
}
