package hr.tvz.pejkunovic.demo;

import baza.BazaPodataka;
import entiteti.*;
import iznimke.NijeOdabranEntitetIznimka;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetaljiUtakmiceController {
    @FXML
    Text ekipa1;
    @FXML
    Text ekipa2;
    @FXML
    Text score1;
    @FXML
    Text score2;

    @FXML
    public TableView<Performans> popisStudenta;
    @FXML
    public TableColumn<Performans, String> imeIgraca;
    @FXML
    public TableColumn<Performans, String> fakultetIgraca;
    List<Performans> performanse=new ArrayList<>();
    public static Integer idZaDalje=null;

    public void initialize(){
        Utakmica utakmica= PopisUtakmicaController.utakmicaZaDalje;
        try {
            Rezultat rezultat=BazaPodataka.dohvatiRezultatPoIdUtakmice(utakmica.getId());
            Fakultet faks1=BazaPodataka.dohvatiFakultetePremaIdu(utakmica.getFaks1Id());
            Fakultet faks2=BazaPodataka.dohvatiFakultetePremaIdu(utakmica.getFaks2Id());

            ekipa1.setText(faks1.getNaziv());
            ekipa2.setText(faks2.getNaziv());
            score1.setText(rezultat.getBrojKosevaDomaci().toString());
            score2.setText(rezultat.getBrojKosevaGosti().toString());

            performanse=BazaPodataka.dohvatiPerformansePoIdUtakmice(utakmica.getId());

            imeIgraca.setCellValueFactory(celldata -> {
                try {
                    return new SimpleStringProperty(BazaPodataka.dohvatiStudentaPremaId(celldata.getValue().getIdStudent()).getIme()+" "+
                            BazaPodataka.dohvatiStudentaPremaId(celldata.getValue().getIdStudent()).getPrezime());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            fakultetIgraca.setCellValueFactory(celldata -> {
                try {
                    return new SimpleStringProperty(BazaPodataka.dohvatiFakultetePremaIdu(BazaPodataka.dohvatiStudentaPremaId(celldata.getValue().getIdStudent()).getFakultetId()).getNaziv());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            popisStudenta.setItems(FXCollections.observableList(performanse));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void otvoriBoxScoreIgraca(){
        Performans performansZaDalje=popisStudenta.getSelectionModel().getSelectedItem();
        try {
            IznimkeMetode.provjeraJeLiObjektOdabran(performansZaDalje);
            idZaDalje=performansZaDalje.getIdStudent();
            try {
                IzbornikAdminController.pokreniBoxScoreIgraca();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }catch(NijeOdabranEntitetIznimka e) {
            e.izbaciGresku("igrac");
        }
    }

    public void natrag() throws IOException {
        PopisUtakmicaController.utakmicaZaDalje=null;
        IzbornikAdminController.pokreniPopisUtakmica();
    }
}
