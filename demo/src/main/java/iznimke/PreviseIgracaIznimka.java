package iznimke;

import javafx.scene.control.Alert;

public class PreviseIgracaIznimka extends Exception{
    public void izbaciGresku() {
        Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Pogresno dodan igrac");
        alert1.setHeaderText("Previse igraca za petorku");
        alert1.setContentText("Mozete imati samo 5 odabranih igraca");
        alert1.showAndWait();
    }
}
