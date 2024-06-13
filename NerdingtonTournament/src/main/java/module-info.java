module ralf.vanaert.nerdingtontournament {
    requires javafx.controls;
    requires javafx.fxml;


    opens ralf.vanaert.nerdingtontournament to javafx.fxml;
    exports ralf.vanaert.nerdingtontournament;
}