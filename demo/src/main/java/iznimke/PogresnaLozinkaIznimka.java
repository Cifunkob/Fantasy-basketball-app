package iznimke;

import javafx.scene.control.Alert;

public class PogresnaLozinkaIznimka extends Exception{
    public void izbaciGresku() {
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Kriva lozinka");
        alert1.setHeaderText("Unesena je pogresna lozinka");
        alert1.setContentText("Molim provjerite lozinku");
        alert1.showAndWait();
    }
}
