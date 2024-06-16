package ralf.vanaert.nerdingtontournament.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private SimpleStringProperty name;
    private List<SimpleIntegerProperty> results;

    private SimpleIntegerProperty score;

    public Player(String name) {
        this.name = new SimpleStringProperty(name);
        this.results = new ArrayList<>();
        this.score = new SimpleIntegerProperty(0);
    }

    public Player() {
        this("");
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

    public IntegerProperty getResultProperty(int index) {
        ensureResultsSize(index + 1);
        return results.get(index);
    }

    public Integer getResult(int index) {
        ensureResultsSize(index + 1);
        return results.get(index).get();
    }

    public void setResult(int index, Integer result) {
        ensureResultsSize(index + 1);
        results.get(index).set(result);
        this.calculateScore();
    }

    private void ensureResultsSize(int size) {
        while (results.size() < size) {
            results.add(new SimpleIntegerProperty(0));
        }
    }

    public SimpleIntegerProperty getScoreProperty() {
        return this.score;
    }

    public int getScore() {
        return this.score.get();
    }

    private void calculateScore() {
        int score = 0;
        for (IntegerProperty each : this.results) {
            score += each.get();
        }
        this.score.set(score);
    }
}
