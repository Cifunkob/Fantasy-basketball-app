package hr.tvz.pejkunovic.demo;

import baza.BazaPodataka;
import entiteti.*;
import iznimke.NijeOdabranEntitetIznimka;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LeaderboardLigeController {
    @FXML
    public TableView<Korisnik> sviKorisniciTableView;
    @FXML
    public TableColumn<Korisnik, String> sviKorisniciImeColumn;
    @FXML
    public TableColumn<Korisnik, String> sviKorisniciBodoviColumn;
    public static Korisnik korisnikZaIzbor=null;
    public void initialize() throws SQLException, IOException {
        List<Korisnik> korisnici = new ArrayList<>();
        List<KorisnikLiga> korisniciLige = new ArrayList<>();
        List<Korisnik> korisniciULigi = new ArrayList<>();
        Liga liga = LigaOdabirController.liga;
        System.out.println(liga);
        korisnici = BazaPodataka.dohvatiSveKorisnike();
        korisniciLige = BazaPodataka.dohvatiKorisnikeLige(liga.getId());
        System.out.println(korisniciLige);
        for (KorisnikLiga korisnikLiga : korisniciLige) {
            Korisnik korisnik = BazaPodataka.dohvatiKorisnikaPremaId(korisnikLiga.getIdKorisnik());
            korisniciULigi.add(korisnik);
            System.out.println(dohvatiBodoveKorisnika(korisnik,liga));
        }

        sviKorisniciImeColumn.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getUsername()));
        sviKorisniciBodoviColumn.setCellValueFactory(celldata-> {
            try {
                return new SimpleStringProperty(dohvatiBodoveKorisnika(celldata.getValue(),liga).toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        /*sviKorisniciBodoviColumn.setCellValueFactory(celldata -> {
            try {
                return new SimpleStringProperty(BazaPodataka.dohvatiBodoveKorisnikaLige(celldata.getValue().getId(),liga.getId()).get().getBrojBodova().toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });*/
        sviKorisniciTableView.setItems(FXCollections.observableList(korisniciULigi));


    }
    public void pokreniInformacijeOKorisniku(){
        korisnikZaIzbor=sviKorisniciTableView.getSelectionModel().getSelectedItem();
        try {
            IznimkeMetode.provjeraJeLiObjektOdabran(korisnikZaIzbor);
            try {
                IzbornikAdminController.pokreniPodatke();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (NijeOdabranEntitetIznimka e){
            e.izbaciGresku("korisnik");
        }
    }
    public void natrag() throws IOException {
        IzbornikAdminController.pokreniAdmina();
    }
    public Double dohvatiBodoveKorisnika(Korisnik korisnik,Liga liga) throws SQLException {
        System.out.println(korisnik.getId());
        Optional<BodoviLiga> bodovi=BazaPodataka.dohvatiBodoveKorisnikaLige(korisnik.getId(),liga.getId());
    return bodovi.get().getBrojBodova();
    }
}
