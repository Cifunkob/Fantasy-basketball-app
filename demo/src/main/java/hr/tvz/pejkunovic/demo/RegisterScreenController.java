package hr.tvz.pejkunovic.demo;

import baza.BazaPodataka;
import entiteti.Enkripcija;
import entiteti.IznimkeMetode;
import entiteti.Korisnik;
import iznimke.DupliKorisnikIznimka;
import iznimke.KratkaLozinkaIznimka;
import iznimke.NesigurnaLozinkaIznimka;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisterScreenController {

    @FXML
    TextField korisnickoTextField;
    @FXML
    PasswordField lozinkaField;
    @FXML
    PasswordField ponoviLozinkuField;
    public void registrirajMe() throws SQLException, IOException{

        Korisnik noviKorisnik=new Korisnik();
        String korisnickoIme=korisnickoTextField.getText();
        String lozinka=lozinkaField.getText();
        String ponovljenaLozinka=ponoviLozinkuField.getText();
        List<Korisnik> korisnici=new ArrayList<>();
        Boolean dupli=false;
        try{
          /*  korisnici=BazaPodataka.dohvatiSveKorisnike();
            for(Korisnik korisnik:korisnici){
                if(korisnik.getUsername().equals(korisnickoIme)){
                    dupli=true;
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Duplo korisnicko ime");
                    alert1.setHeaderText("Korisnicko ime u upotrebi");
                    alert1.setContentText("Molim vas odaberite jedinstveno korisnicko ime");
                    alert1.showAndWait();
                }}*/
            IznimkeMetode.provjeraDuplogKorisnickogImena(korisnickoIme);
            IznimkeMetode.provjeraDuzineLozinke(lozinka);
            IznimkeMetode.provjeraSigurnostiLozinke(lozinka);

            if(!dupli){
            if(lozinka.equals(ponovljenaLozinka)){
                lozinka=Enkripcija.enkriptiraj(lozinka);
                noviKorisnik=new Korisnik(korisnickoIme,lozinka);
                BazaPodataka.unesiNovogKorisnika(noviKorisnik);
                IzbornikAdminController.pokreniLogin();
            }
            else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Lozinke se ne poklapaju");
                alert1.setHeaderText("Lozinke su razlicite");
                alert1.setContentText("Molim vas ponovite istu lozinku");
                alert1.showAndWait();
            }
            }
        } catch (NoSuchAlgorithmException e) {
            
        } catch (DupliKorisnikIznimka e) {
            e.izbaciGresku();
        } catch (KratkaLozinkaIznimka e) {
            e.izbaciGreskuKratkaLozinka();
        } catch (NesigurnaLozinkaIznimka e) {
            e.izbaciGreskuNemaBrojaSlova();
        }

    }

    public void odustani(){
        try {
            IzbornikAdminController.pokreniLogin();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
