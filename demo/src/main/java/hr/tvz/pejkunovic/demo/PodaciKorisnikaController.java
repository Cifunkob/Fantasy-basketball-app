package hr.tvz.pejkunovic.demo;

import baza.BazaPodataka;
import entiteti.Fakultet;
import entiteti.IgracKorisnik;
import entiteti.Korisnik;
import entiteti.Student;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PodaciKorisnikaController {
    @FXML
    Text textKorisnikIme;
    @FXML
    Text textKorisnikPetorka;
    @FXML
    Text textKorisnikBodovi;
    @FXML
    public void initialize() {
        textKorisnikPetorka.setText("");
        List<Student> studenti=new ArrayList<>();
        Korisnik korisnik=LeaderboardLigeController.korisnikZaIzbor;
        try {
            Korisnik korisnik1=BazaPodataka.dohvatiKorisnikaPremaUsernameu(korisnik.getUsername());
            List<IgracKorisnik> listaIgracaKorisnika=BazaPodataka.dohvatiIgraceKorisnika(korisnik1.getId(),LigaOdabirController.liga.getId());
            listaIgracaKorisnika.stream().forEach(igracKorisnik -> {
                try {
                    Student student=BazaPodataka.dohvatiStudentaPremaId(igracKorisnik.getIgracId());
                    studenti.add(student);
                    Double bodovi=BazaPodataka.dohvatiBodoveKorisnikaLige(korisnik1.getId(),LigaOdabirController.liga.getId()).get().getBrojBodova();
                    textKorisnikBodovi.setText(bodovi.toString());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            studenti.stream().forEach(student1 -> {
                textKorisnikPetorka.setText(textKorisnikPetorka.getText()+student1.getIme()+" "+student1.getPrezime()+"\n");
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void natrag() throws IOException {
        LeaderboardLigeController.korisnikZaIzbor=null;
        IzbornikAdminController.pokreniLeadearboardLige();
    }
}

