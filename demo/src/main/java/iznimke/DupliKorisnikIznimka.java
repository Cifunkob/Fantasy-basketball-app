package iznimke;

import javafx.scene.control.Alert;

public class DupliKorisnikIznimka extends Exception {
    public void izbaciGresku() {
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Duplo ime");
        alert1.setHeaderText("Vec postoji korisnik s time imenom");
        alert1.setContentText("Molim unesite jedinstveno korisnicko ime");
        alert1.showAndWait();
    }
}