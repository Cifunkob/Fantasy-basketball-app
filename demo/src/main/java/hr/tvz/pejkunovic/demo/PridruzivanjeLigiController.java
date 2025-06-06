package hr.tvz.pejkunovic.demo;

import baza.BazaPodataka;
import entiteti.BodoviLiga;
import entiteti.Korisnik;
import entiteti.KorisnikLiga;
import entiteti.Liga;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PridruzivanjeLigiController {
    @FXML
    TextField sifraTextField;
    @FXML
    Text imeLige;
    Liga liga;
    public  void initialize(){
        liga=LigaOdabirController.liga;
        imeLige.setText(liga.getIme());
    }
    public void pridruziKorisnikaLigi(){
        Liga liga=LigaOdabirController.liga;
        Korisnik korisnik= LoginScreenController.korisnik;
        String sifra=sifraTextField.getText();
        if(liga.getSifra().equals(sifra)) {
            KorisnikLiga korisnikLiga = new KorisnikLiga(liga.getId(),korisnik.getId());
            BazaPodataka.unesiNovogKorisnikaLige(korisnikLiga);
            BazaPodataka.unesiNoveBodoveLige(new BodoviLiga(korisnik.getId(), liga.getId(), 0.0));

            Stage stage= (Stage) sifraTextField.getScene().getWindow();
            stage.close();
        }
        else {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Pogresna sifra");
            alert1.setHeaderText("Unijeli ste pogresnu sifru");
            alert1.setContentText("Molim unesite ispravnu sifru da bi se pridruzili ligi!");
            alert1.showAndWait();
        }
    }
}
