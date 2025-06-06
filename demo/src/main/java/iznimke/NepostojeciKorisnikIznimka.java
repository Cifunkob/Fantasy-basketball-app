package iznimke;

import javafx.scene.control.Alert;

public class NepostojeciKorisnikIznimka extends Exception{
    public void izbaciGresku() {
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Nepostojeci korisnik");
        alert1.setHeaderText("Uneseno korisnicko ime se ne koristi");
        alert1.setContentText("Molim provjerite korisnicko ime ili se registrirajte");
        alert1.showAndWait();
    }
}
