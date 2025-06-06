package iznimke;

import javafx.scene.control.Alert;

public class NesigurnaLozinkaIznimka extends Exception{
    public void izbaciGreskuNemaBrojaSlova() {
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Nesigurna lozinka");
        alert1.setHeaderText("Lozinka mora sadrzavati barem 1 broj i 1 slovo");
        alert1.setContentText("Molim unesite lozinku sa kombinacijom broja i slova");
        alert1.showAndWait();
    }
}
