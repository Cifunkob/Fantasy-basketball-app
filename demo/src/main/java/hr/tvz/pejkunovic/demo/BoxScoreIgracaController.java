package hr.tvz.pejkunovic.demo;

import baza.BazaPodataka;
import entiteti.BoxScore;
import entiteti.Performans;
import entiteti.Student;
import entiteti.Utakmica;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BoxScoreIgracaController {
    @FXML
    public Text textIme;
    @FXML
    public Text textKosevi;
    @FXML
    public Text textAsistirao;
    @FXML
    public Text textSkokovi;
    @FXML
    public Text textUkradene;
    @FXML
    public Text textBlokovi;
    @FXML
    public Text textOpucaneTrice;
    @FXML
    public Text textOpucaneDvice;
    @FXML
    public Text textOpucanaSlobodna;
    @FXML
    public Text textZabijeneTrice;
    @FXML
    public Text textZabijeneDvice;
    @FXML
    public Text textZabijenaSlobodna;
    @FXML
    public Text textFauli;
    @FXML
    public Text textIzgublljene;
    @FXML
    public Text textPostotakDvice;
    @FXML
    public Text textPostotakTrice;
    @FXML
    public Text textPostotakSlobodnih;




    public void initialize() {
        try {
            Student student=BazaPodataka.dohvatiStudentaPremaId(DetaljiUtakmiceController.idZaDalje);
            Utakmica utakmica=PopisUtakmicaController.utakmicaZaDalje;
            Performans performans=BazaPodataka.dohvatiPerformansePoIdUtakmiceIdStudenta(utakmica.getId(),student.getId());
            BoxScore boxScore=BazaPodataka.dohvatiBoxScorePoId(performans.getIdBoxScore());
            Integer zabijenoKoseva=0;
            Integer asistencije=0;
            Integer skokovi=0;
            Integer ukradene=0;
            Integer blokovi=0;
            Integer opucaneTrice=0;
            Integer opucanDvice=0;
            Integer opucanaSlobodna=0;
            Integer zabijeneTrice=0;
            Integer zabijeneDvice=0;
            Integer zabijenaSlobodna=0;
            Integer fauli=0;
            Integer izgubljene=0;
                zabijenoKoseva+=boxScore.getKosevi();
                asistencije+=boxScore.getAsistencije();
                skokovi+= boxScore.getSkokovi();
                ukradene+=boxScore.getUkradene();
                blokovi+=boxScore.getBlokovi();
                opucaneTrice+= boxScore.getOpucaneTrice();
                opucanDvice+= boxScore.getOpucaneDvice();
                opucanaSlobodna+= boxScore.getOpucanaSlobodna();
                zabijeneTrice+=boxScore.getZabijeneTrice();
                zabijeneDvice+= boxScore.getZabijeneDvice();
                zabijenaSlobodna+=boxScore.getZabijenaSlobodna();
                fauli+= boxScore.getFauli();
                izgubljene+= boxScore.getIzgubljene();
            textIme.setText(student.getIme()+" "+student.getPrezime());
            textKosevi.setText(zabijenoKoseva.toString());
            textAsistirao.setText(asistencije.toString());
            textSkokovi.setText(skokovi.toString());
            textUkradene.setText(ukradene.toString());
            textBlokovi.setText(blokovi.toString());
            textOpucaneTrice.setText(opucaneTrice.toString());
            textOpucaneDvice.setText(opucanDvice.toString());
            textOpucanaSlobodna.setText(opucanaSlobodna.toString());
            textZabijeneTrice.setText(zabijeneTrice.toString());
            textZabijeneDvice.setText(zabijeneDvice.toString());
            textZabijenaSlobodna.setText(zabijenaSlobodna.toString());
            textFauli.setText(fauli.toString());
            textIzgublljene.setText(izgubljene.toString());

            double postotakTrice=(double) zabijeneTrice/opucaneTrice;
            double postotakDvice=(double) zabijeneDvice/opucanDvice;
            double postotakSlobodnih=(double) zabijenaSlobodna/opucanaSlobodna;


            String formattedPoUtakmici = String.format("%.2f", postotakDvice);
            textPostotakDvice.setText(formattedPoUtakmici);
            formattedPoUtakmici = String.format("%.2f", postotakTrice);
            textPostotakTrice.setText(formattedPoUtakmici);
            formattedPoUtakmici = String.format("%.2f", postotakSlobodnih);
            textPostotakSlobodnih.setText(formattedPoUtakmici);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void natrag(){
        DetaljiUtakmiceController.idZaDalje=null;
        try {
            IzbornikAdminController.pokreniDetaljeUtakmice();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
