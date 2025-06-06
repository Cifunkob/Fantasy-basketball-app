package hr.tvz.pejkunovic.demo;

import baza.BazaPodataka;
import entiteti.*;
import iznimke.NijeOdabranEntitetIznimka;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LigaOdabirController {
    @FXML
    public TableView<Liga> popisLiga;
    @FXML
    public TableColumn<Liga, String> ligeIme;
    public static List<Liga> lige = new ArrayList<>();
    public static Liga liga;
    public void initialize() {

        try {
            lige = BazaPodataka.dohvatiSveLige();

            ligeIme.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getIme()));

            popisLiga.setItems(FXCollections.observableList(lige));

        } catch (SQLException | IOException ex) {
        }

    }
    public void unesiNovuLigu(){
        Stage newStage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(UnosNoveLigeController.class.getResource("unosNoveLige.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 500, 600);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newStage.setTitle("Ekran za login");
        newStage.setScene(scene);
        newStage.show();
        newStage.setOnHiding((WindowEvent event) ->{
            azurirajPrikaz();
        });
    }
    public static List<String> dohvatiSvaPostojecaImena(){
        List<String> imena=new ArrayList<>();
        lige.stream().forEach(liga -> {
            imena.add(liga.getIme());
        });
        return imena;
    }
    public void azurirajPrikaz(){
       initialize();
    }
    public void dodajKorisnikaULigu(){
        liga=popisLiga.getSelectionModel().getSelectedItem();
        try {
            IznimkeMetode.provjeraJeLiObjektOdabran(liga);
            Korisnik korisnik = LoginScreenController.korisnik;
            List<KorisnikLiga> listaKorisnikaLige = new ArrayList<>();
            Boolean pridruzen = false;
            try {
                listaKorisnikaLige = BazaPodataka.dohvatiKorisnikeLige(liga.getId());
                for (KorisnikLiga korisnikLiga : listaKorisnikaLige) {
                    if (korisnikLiga.getIdKorisnik().equals(korisnik.getId())) {
                        pridruzen=true;
                        IzbornikAdminController.pokreniAdmina();
                    }
                }
                if (pridruzen == false) {
                    Stage newStage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(PridruzivanjeLigiController.class.getResource("pridruzivanjeLigi.fxml"));
                    Scene scene = null;
                    try {
                        scene = new Scene(fxmlLoader.load(), 500, 600);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    newStage.setTitle("Pridruzi se ligi");
                    newStage.setScene(scene);
                    newStage.show();
                    newStage.setOnHiding(event -> {
                        System.out.println("Odustao");
                    });
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }catch (NijeOdabranEntitetIznimka e){
            e.izbaciGresku("liga");
        }
    }
    public void odjaviMe(){
        LoginScreenController.korisnik=null;
        try {
            IzbornikAdminController.pokreniLogin();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
