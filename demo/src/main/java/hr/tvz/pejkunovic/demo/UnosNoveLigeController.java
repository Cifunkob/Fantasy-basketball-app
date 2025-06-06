package hr.tvz.pejkunovic.demo;

import baza.BazaPodataka;
import entiteti.Liga;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class UnosNoveLigeController {
    @FXML
    TextField textfieldIme;
    @FXML
    TextField textFieldSifra;
    public void unesiPodatke(){
        String ime=textfieldIme.getText();
        String sifra=textFieldSifra.getText();
        AtomicReference<Boolean> uvjet= new AtomicReference<>(false);
        List<String> imena= LigaOdabirController.dohvatiSvaPostojecaImena();
        imena.stream().forEach(imeLige->{
            if(imeLige.equals(ime)){
                uvjet.set(true);
            }
        });
        if(uvjet.get()==false) {
            Liga liga = new Liga(ime, sifra);
            BazaPodataka.unesiNovuLigu(liga);
            Stage stage= (Stage) textfieldIme.getScene().getWindow();
            stage.close();
        }
        else {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Duplikat");
            alert1.setHeaderText("Vec postoji liga s tim imenom");
            alert1.setContentText("Molim unesite jednistveno ime");
            alert1.showAndWait();

        }
    }
}
