package hr.tvz.pejkunovic.demo;

import baza.BazaPodataka;
import entiteti.IznimkeMetode;
import entiteti.Liga;
import entiteti.Utakmica;
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

public class PopisUtakmicaController {
    @FXML
    public TableView<Utakmica> popisUtakmica;
    @FXML
    public TableColumn<Utakmica, String> domacaEkipa;
    @FXML
    public TableColumn<Utakmica, String> gostiEkipa;
    List<Utakmica> utakmice=new ArrayList<>();
    public static Utakmica utakmicaZaDalje=new Utakmica();

    public void initialize() {
        try {
            utakmice = BazaPodataka.dohvatiSveUtakmice();
            System.out.println(utakmice);
            domacaEkipa.setCellValueFactory(celldata -> {
                try {
                    return new SimpleStringProperty(BazaPodataka.dohvatiFakultetePremaIdu(celldata.getValue().getFaks1Id()).getNaziv());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            gostiEkipa.setCellValueFactory(celldata -> {
                try {
                    return new SimpleStringProperty(BazaPodataka.dohvatiFakultetePremaIdu(celldata.getValue().getFaks2Id()).getNaziv());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            popisUtakmica.setItems(FXCollections.observableList(utakmice));

        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
    public void otvoriDetaljeUtakmice(){
        utakmicaZaDalje=popisUtakmica.getSelectionModel().getSelectedItem();
       try {
           IznimkeMetode.provjeraJeLiObjektOdabran(utakmicaZaDalje);
           try {
               IzbornikAdminController.pokreniDetaljeUtakmice();
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       }catch (NijeOdabranEntitetIznimka e){
           e.izbaciGresku("utakmica");
       }
    }

    public void natrag(){
        try {
            IzbornikAdminController.pokreniAdmina();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
