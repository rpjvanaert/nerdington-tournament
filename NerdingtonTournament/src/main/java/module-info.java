module ralf.vanaert.nerdingtontournament {
    requires javafx.controls;
    requires javafx.fxml;


    opens ralf.vanaert.nerdingtontournament to javafx.fxml;
    exports ralf.vanaert.nerdingtontournament;
    exports ralf.vanaert.nerdingtontournament.Model;
    exports ralf.vanaert.nerdingtontournament.Controller;
    opens ralf.vanaert.nerdingtontournament.Controller to javafx.fxml;
}