package ralf.vanaert.nerdingtontournament;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TournamentApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TournamentApplication.class.getResource("tournament-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Nerdington Tournament");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}