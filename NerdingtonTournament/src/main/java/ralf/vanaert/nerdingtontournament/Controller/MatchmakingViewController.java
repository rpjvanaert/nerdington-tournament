package ralf.vanaert.nerdingtontournament.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    @FXML
    private Button reroll;

    private ObservableList<AssignedGame> assignedGames;

    private List<Player> players;
    private List<Game> games;
    private boolean playersRandom;

    public void initMatchmaking(ObservableList<Player> players, ObservableList<Game> games, boolean random) {
        setData(players, games, random);

        for (int attempt = 1; attempt <= 10; attempt++) {
            if (rollMatchmaking()) break;
        }
    }

    private boolean rollMatchmaking() {
        List<Player> unassignedPlayers = new ArrayList<>(this.players);
        List<Game> unassignedGames = new ArrayList<>(this.games);

        Random random = new Random();
        Collections.shuffle(unassignedGames, random);
        if (this.playersRandom) Collections.shuffle(unassignedPlayers, random);

        this.assignedGames.clear();

        while (!unassignedPlayers.isEmpty()) {
            if (unassignedPlayers.size() == 1) return false;

            AssignedGame game = null;

            List<Integer> possibleSizes = determinePossibleSizes(unassignedPlayers);
            Collections.sort(possibleSizes);

            if (possibleSizes.isEmpty()) {
                return false;
            }

            Game chosenGame = null;
            Game contender;
            int tooLongCount = 0;
            while (chosenGame == null) {
                contender = unassignedGames.get(random.nextInt(unassignedGames.size()));

                if (contender.getMinPlayers() <= possibleSizes.get(0)){
                    chosenGame = contender;
                    unassignedGames.remove(contender);
                } else {
                    tooLongCount++;

                    if (tooLongCount >= 10) {
                        return false;
                    }
                }
            }

            Iterator<Integer> iterator = possibleSizes.iterator();
            while (iterator.hasNext()) {
                int possibleSize = iterator.next();
                if (possibleSize < chosenGame.getMinPlayers() || possibleSize > chosenGame.getMaxPlayers()) {
                    iterator.remove();
                }
            }

            int amountPlayers = possibleSizes.get(random.nextInt(possibleSizes.size()));

            List<Player> assignedPlayers = new ArrayList<>();
            for (int i = 0; i < amountPlayers; i++) {
                assignedPlayers.add(unassignedPlayers.remove(0));
            }

            game = new AssignedGame(chosenGame, assignedPlayers);

            this.assignedGames.add(game);
        }

        return true;
    }

    private Game pickGame(int numberPlayers) {
        return null;
    }

    private void setData(ObservableList<Player> players, ObservableList<Game> games, boolean random) {
        this.players = new ArrayList<>(players);
        this.games = new ArrayList<>(games);
        this.playersRandom = random;
        this.players.sort(Comparator.comparingInt(Player::getScore).reversed());
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

        this.reroll.setOnAction(event -> {
            rollMatchmaking();
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
