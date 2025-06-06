package iznimke;

import javafx.scene.control.Alert;

public class KratkaLozinkaIznimka extends Exception{
    public void izbaciGreskuKratkaLozinka() {
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Kratka lozinka");
        alert1.setHeaderText("Lozinka je prekratka");
        alert1.setContentText("Molim unesite lozinku duzu od 6 znakova");
        alert1.showAndWait();
    }

}
