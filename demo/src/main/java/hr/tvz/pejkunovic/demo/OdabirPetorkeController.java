package hr.tvz.pejkunovic.demo;

import baza.BazaPodataka;
import entiteti.*;
import iznimke.IgracNemaOdigranuUtakmicuIznimka;
import iznimke.NijeOdabranEntitetIznimka;
import iznimke.PreviseIgracaIznimka;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OdabirPetorkeController{

    private static Logger logger= LoggerFactory.getLogger(OdabirPetorkeController.class);

    private Integer idKorisnik;
    public static Student studentZaIzbor=null;
    public Liga liga;


    List<Student> studenti;
    List<Student> odabraniStudenti;
    List<Student> filtriraniStudenti;
    @FXML
    TextField pretragaPoImenuTextField;
    @FXML
    TextField pretragaPoPozicijiTextField;
    @FXML
    TextField pretragaPoFakultetuTextField;
    @FXML
    public TableView<Student> sviStudentiTableView;
    @FXML
    public TableView<Student> odabraniStudentiTableView;
    @FXML
    public TableColumn<Student,String> sviStudentiImeColumn;
    @FXML
    public TableColumn<Student,String> sviStudentiFakultetColumn;
    @FXML
    public TableColumn<Student,String> sviStudentiPozicijaColumn;
    @FXML
    public TableColumn<Student,String> odabraniStudentiImeColumn;
    @FXML
    public TableColumn<Student,String> odabraniStudentiFakultetColumn;
    @FXML
    public TableColumn<Student,String> odabraniStudentiPozicijaColumn;
    @FXML
    public Button dodajStudentaUPetorkuButton;
    @FXML
    public Button makniStudentaIzPetorkeButton;
    @FXML
    public Button pretraziPoFakultetuButton;


    public  void initialize(){
        liga=LigaOdabirController.liga;
        idKorisnik=LoginScreenController.idKorisnik;
        studenti = new ArrayList<>();
        List<Student> dostupniStudenti=new ArrayList<>();
        try {
            studenti = BazaPodataka.dohvatiSlobodneStudente(liga.getId());

            filtriraniStudenti=studenti;


            sviStudentiImeColumn.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getIme()+' '+celldata.getValue().getPrezime()));
            sviStudentiFakultetColumn.setCellValueFactory(celldata -> {
                try {
                    return new SimpleStringProperty(BazaPodataka.dohvatiFakultetePremaIdu(Integer.valueOf(celldata.getValue().getFakultetId())).getNaziv());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            sviStudentiPozicijaColumn.setCellValueFactory(celldata -> new SimpleStringProperty(String.valueOf(celldata.getValue().getPozicija())));

            sviStudentiTableView.setItems(FXCollections.observableList(studenti));
            postaviPodatkeOOdabranomUTablicu();





        } catch(SQLException ex) {
            logger.error("Greska pri dohvatu podataka iz baze");
            System.out.println("Pogreška kod spajanja na bazu!");
            ex.printStackTrace();}
    }
    public void dohvatiPoKriteriju() throws SQLException {

        filtriraniStudenti=new ArrayList<>();
        String imeString = pretragaPoImenuTextField.getText();
        String fakultetString = pretragaPoFakultetuTextField.getText();
        String pozicijaString = pretragaPoPozicijiTextField.getText();

        List<Fakultet> listaFakultet=new ArrayList<>();
        try {
            listaFakultet=BazaPodataka.dohvatiFakultetePremaImenu(fakultetString);
            System.out.println(listaFakultet.toString()+"FAKSOVI");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(fakultetString +"fakultetString");

        if (!Objects.equals(fakultetString, "")){
        for(Fakultet fakultet: listaFakultet){
            System.out.println("TRAZIM ZA STUDENTA SA FAKULTETA "+imeString+ " "+fakultet.getId());
            Student noviStudent = new Student(0,imeString,"",fakultet.getId(),pozicijaString, LocalDate.now());
            try {
                filtriraniStudenti.addAll(BazaPodataka.dohvatiStudentePremaKriterijima(noviStudent,liga.getId()));
                System.out.println("DODANI SU STUDENTi"+ filtriraniStudenti.toString());
            }catch (SQLException e){
                logger.info("SQL EXCEPTION");
            }
        }}
        else {
            Student noviStudent = new Student(0,imeString,"",0,pozicijaString, LocalDate.now());
            try {
                System.out.println("IDEM KROZ ELSE");
                filtriraniStudenti.addAll(BazaPodataka.dohvatiStudentePremaKriterijima(noviStudent,liga.getId()));
            }catch (SQLException e){
                logger.info("SQL EXCEPTION");
            }
        }

        System.out.println(filtriraniStudenti.toString());
        sviStudentiTableView.setItems(FXCollections.observableList(filtriraniStudenti));


    }
    public void dodajStudentaUPetorku(){
        Student studentZaDodati=sviStudentiTableView.getSelectionModel().getSelectedItem();
        try {
        IznimkeMetode.provjeraJeLiObjektOdabran(studentZaDodati);

            Integer brojIgracaKorisnika = 0;
            Boolean jeLiOdabran = true;
            try{
                IznimkeMetode.provjeraImaLiKorisnikManjeOd5Igraca();

            try {
                Kljuc kljuc = new Kljuc();
                do {
                    kljuc = BazaPodataka.dohvatiKljuc();
                } while (kljuc.getZauzet() == 1);
                kljuc.setZauzet(1);
                BazaPodataka.promjeniKljuc(kljuc);

                    filtriraniStudenti = BazaPodataka.dohvatiSlobodneStudente(liga.getId());
                    for (Student student : filtriraniStudenti) {
                        if (student.getId().equals( studentZaDodati.getId())) {
                            jeLiOdabran = false;
                        }
                    }
                    if (!jeLiOdabran) {
                        BazaPodataka.dodajStudentaUPetorku(studentZaDodati.getId(), idKorisnik, liga.getId());
                        postaviPodatkeOOdabranomUTablicu();
                    } else {
                        kljuc.setZauzet(0);
                        BazaPodataka.promjeniKljuc(kljuc);
                        Alert alert1 = new Alert(Alert.AlertType.ERROR);
                        alert1.setTitle("Zauzet igrac");
                        alert1.setHeaderText("Zakasnili ste");
                        alert1.setContentText("Netko je ugrabio igraca prije vas!");
                        alert1.showAndWait();
                        azurirajTablicu();
                    }
                kljuc.setZauzet(0);
                BazaPodataka.promjeniKljuc(kljuc);
            } catch (SQLException e) {
                logger.info("SQL EXCEPTION");
            }
            } catch (PreviseIgracaIznimka e) {
                e.izbaciGresku();
            }
        }catch (NijeOdabranEntitetIznimka e){
            e.izbaciGresku("dodavanje");
        }

    }
    public void postaviPodatkeOOdabranomUTablicu(){
        odabraniStudenti = new ArrayList<>();
        IgracKorisnik igracKorisnik=new IgracKorisnik();
        List<IgracKorisnik> listaIgracKorisnika=new ArrayList<>();
        try {
            listaIgracKorisnika=BazaPodataka.dohvatiIgraceKorisnika(idKorisnik,liga.getId());
            for(IgracKorisnik igracKorinik1 : listaIgracKorisnika){
               Student student = BazaPodataka.dohvatiStudentaPremaId(igracKorinik1.getIgracId());
                System.out.println("DODANI STUDENT" + student);
               odabraniStudenti.add(student);
            }

            odabraniStudentiImeColumn.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getIme()+' '+celldata.getValue().getPrezime()));
            odabraniStudentiFakultetColumn.setCellValueFactory(celldata -> {
                try {
                    return new SimpleStringProperty(BazaPodataka.dohvatiFakultetePremaIdu(celldata.getValue().getFakultetId()).getNaziv());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            odabraniStudentiPozicijaColumn.setCellValueFactory(celldata -> new SimpleStringProperty(String.valueOf(celldata.getValue().getPozicija())));

            odabraniStudentiTableView.setItems(FXCollections.observableList(odabraniStudenti));

            azurirajTablicu();

        } catch(SQLException ex) {
            logger.error("Greska pri dohvatu podataka iz baze");
            System.out.println("Pogreška kod spajanja na bazu!");
            ex.printStackTrace();}
    }
    public void makniStudentaIzPetorke(){
        Student studentZaMaknuti=odabraniStudentiTableView.getSelectionModel().getSelectedItem();
        try{
            IznimkeMetode.provjeraJeLiObjektOdabran(studentZaMaknuti);
        try {
            System.out.println("Stud,Kor: "+studentZaMaknuti.getId()+" "+idKorisnik);
                BazaPodataka.makniStudentaIzPetorke(studentZaMaknuti.getId(),idKorisnik);
                postaviPodatkeOOdabranomUTablicu();
            try {
                azurirajTablicu();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }catch (SQLException e){
            logger.info("SQL EXCEPTION");
        } catch (IOException e) {
            logger.info("Runtime EXCEPTION");
            throw new RuntimeException(e);
        }
    }catch (NijeOdabranEntitetIznimka e){
            e.izbaciGresku("micanje");
        }
    }
    public void otvoriSimulator(){
        try {
            IzbornikAdminController.pokreniSimulator();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void otvoriInformacijeOStudentu(){
        studentZaIzbor=sviStudentiTableView.getSelectionModel().getSelectedItem();
        try {
            IznimkeMetode.provjeraJeLiObjektOdabran(studentZaIzbor);
        }catch (NijeOdabranEntitetIznimka e){
            e.izbaciGresku("igrac");}
try{
            IznimkeMetode.provjeraImaLiIgracOdigranuUtakmicu(studentZaIzbor.getId());
            try {
                IzbornikAdminController.pokreniInformacijeOStudentu();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }catch (IgracNemaOdigranuUtakmicuIznimka e){
            e.izbaciGresku();
        }
    }
    public void otvoriLeaderboardLige(){
        try {
            IzbornikAdminController.pokreniLeadearboardLige();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void otvoriPopisUtakmica(){
        try {

            IzbornikAdminController.pokreniPopisUtakmica();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void promjeniLigu(){
        LigaOdabirController.liga=null;
        try {
            IzbornikAdminController.pokreniOdabirLiga();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void azurirajTablicu() throws SQLException {
        if(pretragaPoFakultetuTextField.getText().isEmpty() && pretragaPoPozicijiTextField.getText().isEmpty() && pretragaPoImenuTextField.getText().isEmpty()){
        filtriraniStudenti=BazaPodataka.dohvatiSlobodneStudente(liga.getId());
        sviStudentiTableView.setItems(FXCollections.observableList(filtriraniStudenti));
        }else {
            dohvatiPoKriteriju();
        }
    }
}
