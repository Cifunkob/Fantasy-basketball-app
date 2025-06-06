package hr.tvz.pejkunovic.demo;

import baza.BazaPodataka;
import entiteti.Enkripcija;
import entiteti.IznimkeMetode;
import entiteti.Korisnik;
import iznimke.NepostojeciKorisnikIznimka;
import iznimke.PogresnaLozinkaIznimka;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class LoginScreenController extends Application {

    public static Integer idKorisnik;
    public static Korisnik korisnik;
    public static Integer rola=0;
    @FXML
    public TextField korisnickoImeTextField;
    @FXML
    public PasswordField lozinkaPasswordField;
    public static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(LoginScreenController.class.getResource("loginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 750);
        stage.setTitle("Ekran za login");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
    public static Stage getMainStage() {
        return mainStage;
    }
    public void ulogirajMe() throws SQLException, IOException {
      //IzbornikAdminController.pokreniSimulator();

        Korisnik noviKorisnik=new Korisnik();
        String username=korisnickoImeTextField.getText();
        String lozinka=lozinkaPasswordField.getText();

        try{
            IznimkeMetode.provjeraKorisnikaPriPrijavi(username);
           noviKorisnik= BazaPodataka.dohvatiKorisnikaPremaUsernameu(username);
           /*if(Objects.equals(noviKorisnik.getPassword(), Enkripcija.enkriptiraj(password))){
               idKorisnik=noviKorisnik.getId();
               korisnik=noviKorisnik;
               IzbornikAdminController.pokreniOdabirLiga();
           }
           else {
               System.out.println("KRIV PASS");
           }*/
            IznimkeMetode.provjeraLozinkePriPrijavi(lozinka,username);
            idKorisnik=noviKorisnik.getId();
            korisnik=noviKorisnik;
            rola= noviKorisnik.getRolaId();
            IzbornikAdminController.pokreniOdabirLiga();

       }catch (SQLException sqlException){
       } catch (NepostojeciKorisnikIznimka e) {
             e.izbaciGresku();
        } catch (PogresnaLozinkaIznimka e) {
            e.izbaciGresku();
        }

    }
    public void otvoriRegistraciju(){
        try {
            IzbornikAdminController.pokreniRegistraciju();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
