package iznimke;

import javafx.scene.control.Alert;

public class IgracNemaOdigranuUtakmicuIznimka extends RuntimeException{
    public void izbaciGresku() {
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Nema utakmice");
        alert1.setHeaderText("Igrac jos nije odigrao utakmicu");
        alert1.setContentText("Odabrani igrac jos nije odigrao utakmicu");
        alert1.showAndWait();
    }
}
