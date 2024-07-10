package ralf.vanaert.nerdingtontournament.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

public class Game {
    private final StringProperty name;
    private final IntegerProperty minPlayers;
    private final IntegerProperty maxPlayers;

    public Game(String name, int minPlayers, int maxPlayers) {
        this.name = new SimpleStringProperty(name);
        this.minPlayers = new SimpleIntegerProperty(minPlayers);
        this.maxPlayers = new SimpleIntegerProperty(maxPlayers);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public int getMinPlayers() {
        return minPlayers.get();
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers.set(minPlayers);
    }

    public IntegerProperty minPlayersProperty() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers.get();
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers.set(maxPlayers);
    }

    public IntegerProperty maxPlayersProperty() {
        return maxPlayers;
    }
}
