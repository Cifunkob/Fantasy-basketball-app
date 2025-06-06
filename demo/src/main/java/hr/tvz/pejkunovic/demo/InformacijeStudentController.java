package hr.tvz.pejkunovic.demo;

import baza.BazaPodataka;
import entiteti.*;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class InformacijeStudentController {

    @FXML
    public Text textIme;
    @FXML
    public Text textKosevi;
    @FXML
    public Text textKoseviPoUtakmici;
    @FXML
    public Text textOdigraoUtakmica;
    @FXML
    public Text textAsistirao;
    @FXML
    public Text textAsistiraoPoUtakmici;
    @FXML
    public Text textSkokovi;
    @FXML
    public Text textSkokoviPoUtakmici;
    @FXML
    public Text textUkradene;
    @FXML
    public Text textUkradenePoUtakmici;
    @FXML
    public Text textBlokovi;
    @FXML
    public Text textBlokoviPoUtakmici;
    @FXML
    public Text textOpucaneTrice;
    @FXML
    public Text textOpucaneTricePoUtakmici;
    @FXML
    public Text textOpucaneDvice;
    @FXML
    public Text textOpucaneDvicePoUtakmici;
    @FXML
    public Text textOpucanaSlobodna;
    @FXML
    public Text textOpucanaSlobodnaPoUtakmici;
    @FXML
    public Text textZabijeneTrice;
    @FXML
    public Text textZabijeneTricePoUtakmici;
    @FXML
    public Text textZabijeneDvice;
    @FXML
    public Text textZabijeneDvicePoUtakmici;
    @FXML
    public Text textZabijenaSlobodna;
    @FXML
    public Text textZabijenaSlobodnaPoUtakmici;
    @FXML
    public Text textFauli;
    @FXML
    public Text textFauliPoUtakmici;
    @FXML
    public Text textIzgublljene;
    @FXML
    public Text textIzgubljenePoUtakmici;
    @FXML
    public Text textPostotakDvice;
    @FXML
    public Text textPostotakTrice;
    @FXML
    public Text textPostotakSlobodnih;

    public void initialize() {
       Student student=OdabirPetorkeController.studentZaIzbor;
        try {
            List<BoxScore> boxScoreLista=BazaPodataka.dohvatiBoxScorePoIdStudenta(student.getId());
            Integer odigranoUtakmica=boxScoreLista.size();
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

            for(BoxScore boxScore:boxScoreLista){
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
            }
            textIme.setText(student.getIme()+" "+student.getPrezime());
            textOdigraoUtakmica.setText(odigranoUtakmica.toString());
            textKosevi.setText(zabijenoKoseva.toString());
            double poUtakmici= (double) (zabijenoKoseva/odigranoUtakmica);
            String formattedPoUtakmici = String.format("%.1f", poUtakmici);
            textKoseviPoUtakmici.setText(formattedPoUtakmici);
            textAsistirao.setText(asistencije.toString());
            poUtakmici=(double) asistencije/odigranoUtakmica;
            formattedPoUtakmici = String.format("%.1f", poUtakmici);
            textAsistiraoPoUtakmici.setText(formattedPoUtakmici);
            textSkokovi.setText(skokovi.toString());
            poUtakmici=(double) skokovi/odigranoUtakmica;
            formattedPoUtakmici = String.format("%.1f", poUtakmici);
            textSkokoviPoUtakmici.setText(formattedPoUtakmici);
            textUkradene.setText(ukradene.toString());
            poUtakmici=(double) ukradene/odigranoUtakmica;
            formattedPoUtakmici = String.format("%.1f", poUtakmici);
            textUkradenePoUtakmici.setText(formattedPoUtakmici);
            textBlokovi.setText(blokovi.toString());
            poUtakmici=(double) blokovi/odigranoUtakmica;
            formattedPoUtakmici = String.format("%.1f", poUtakmici);
            textBlokoviPoUtakmici.setText(formattedPoUtakmici);
            textOpucaneTrice.setText(opucaneTrice.toString());
            poUtakmici=(double) opucaneTrice/odigranoUtakmica;
            formattedPoUtakmici = String.format("%.1f", poUtakmici);
            textOpucaneTricePoUtakmici.setText(formattedPoUtakmici);
            textOpucaneDvice.setText(opucanDvice.toString());
            poUtakmici=(double) opucanDvice/odigranoUtakmica;
            formattedPoUtakmici = String.format("%.1f", poUtakmici);
            textOpucaneDvicePoUtakmici.setText(formattedPoUtakmici);
            textOpucanaSlobodna.setText(opucanaSlobodna.toString());
            poUtakmici=(double) opucanaSlobodna/odigranoUtakmica;
            formattedPoUtakmici = String.format("%.1f", poUtakmici);
            textOpucanaSlobodnaPoUtakmici.setText(formattedPoUtakmici);
            textZabijeneTrice.setText(zabijeneTrice.toString());
            poUtakmici=(double) zabijeneTrice/odigranoUtakmica;
            formattedPoUtakmici = String.format("%.1f", poUtakmici);
            textZabijeneTricePoUtakmici.setText(formattedPoUtakmici);
            textZabijeneDvice.setText(zabijeneDvice.toString());
            poUtakmici=(double) zabijeneDvice/odigranoUtakmica;
            formattedPoUtakmici = String.format("%.1f", poUtakmici);
            textZabijeneDvicePoUtakmici.setText(formattedPoUtakmici);
            textZabijenaSlobodna.setText(zabijenaSlobodna.toString());
            poUtakmici=(double) zabijenaSlobodna/odigranoUtakmica;
            formattedPoUtakmici = String.format("%.1f", poUtakmici);
            textZabijenaSlobodnaPoUtakmici.setText(formattedPoUtakmici);
            textFauli.setText(fauli.toString());
            poUtakmici=(double) fauli/odigranoUtakmica;
            formattedPoUtakmici = String.format("%.1f", poUtakmici);
            textFauliPoUtakmici.setText(formattedPoUtakmici);
            textIzgublljene.setText(izgubljene.toString());
            poUtakmici=(double) izgubljene/odigranoUtakmica;
            formattedPoUtakmici = String.format("%.1f", poUtakmici);
            textIzgubljenePoUtakmici.setText(formattedPoUtakmici);

            double postotakTrice=(double) zabijeneTrice/opucaneTrice;
            double postotakDvice=(double) zabijeneDvice/opucanDvice;
            double postotakSlobodnih=(double) zabijenaSlobodna/opucanaSlobodna;

            formattedPoUtakmici = String.format("%.2f", postotakDvice);
            textPostotakDvice.setText(formattedPoUtakmici);
            formattedPoUtakmici = String.format("%.2f", postotakTrice);
            textPostotakTrice.setText(formattedPoUtakmici);
            formattedPoUtakmici = String.format("%.2f", postotakSlobodnih);
            textPostotakSlobodnih.setText(formattedPoUtakmici);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void otvoriOdabirPetorke(){
        try {
            IzbornikAdminController.pokreniAdmina();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
