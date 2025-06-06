package hr.tvz.pejkunovic.demo;

import baza.BazaPodataka;
import entiteti.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class SimulatorUtakmiceController {

    @FXML
    public ChoiceBox<String> fakultetPrviChoiceBox;
    @FXML
    public ChoiceBox<String> fakultetDrugiChoiceBox;
    @FXML
    public Button button;
    List<Student> pocetnaPetorkaJedan = new ArrayList<>();
    List<Student> pocetnaPetorkaDva = new ArrayList<>();
    List<BoxScore> boxScoreSvi = new ArrayList<>();

    List<Korisnik> korisnici = new ArrayList<>();
    List<IgracKorisnik> igraciKorisnika = new ArrayList<>();
    List<Performans> performansiLista = new ArrayList<>();
    Integer posjed = 1;
    Integer napad = 1;
    Integer obrana = 2;
    Integer brojKosevaDomaci=0;
    Integer brojKosevaGosti=0;


    @FXML
    public void initialize() {
        List<Fakultet> fakulteti;
        try {
            fakulteti = BazaPodataka.dohvatiSveFakultete();
            korisnici = BazaPodataka.dohvatiSveKorisnike();

            List<String> imenaFakulteta = new ArrayList<>();
            fakulteti.stream().forEach(faks -> imenaFakulteta.add(faks.getNaziv()));
            fakultetPrviChoiceBox.getItems().addAll(imenaFakulteta);
            fakultetDrugiChoiceBox.getItems().addAll(imenaFakulteta);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void simuliraj() {
        try {
            Random random = new Random();
            Map<Student, Stats> mapaStudStat = new HashMap<>();
            Optional<Fakultet> fakultet1 = BazaPodataka.dohvatiFakultetePremaImenu(fakultetPrviChoiceBox.getValue()).stream().findFirst();
            Optional<Fakultet> fakultet2 = BazaPodataka.dohvatiFakultetePremaImenu(fakultetDrugiChoiceBox.getValue()).stream().findFirst();
            List<Student> studentiFaklutetaJedan = BazaPodataka.dohvatiStudenteSFakulteta(fakultet1.get().getId());
            List<Student> studentiFaklutetaDva = BazaPodataka.dohvatiStudenteSFakulteta(fakultet2.get().getId());
            List<String> zauzetePozicije = new ArrayList<>();
            Utakmica utakmica = new Utakmica(fakultet1.get().getId(), fakultet2.get().getId());

            studentiFaklutetaJedan.stream().forEach(student -> boxScoreSvi.add(new BoxScore(student.getId())));
            studentiFaklutetaDva.stream().forEach(student -> boxScoreSvi.add(new BoxScore(student.getId())));

            for (Student student : studentiFaklutetaJedan) {
                if (zauzetePozicije.contains(student.getPozicija())) {
                } else {
                    pocetnaPetorkaJedan.add(student);
                    zauzetePozicije.add(student.getPozicija());
                    mapaStudStat.put(student, BazaPodataka.dohvatiStatsStudenta(student.getId()));
                }
            }


            zauzetePozicije = new ArrayList<>();
            for (Student student : studentiFaklutetaDva) {
                if (zauzetePozicije.contains(student.getPozicija())) {
                } else {
                    pocetnaPetorkaDva.add(student);
                    zauzetePozicije.add(student.getPozicija());
                    mapaStudStat.put(student, BazaPodataka.dohvatiStatsStudenta(student.getId()));
                }
            }


            Student asistent = new Student();
            Student napadac = new Student();
            Integer brojNapada = random.nextInt(20 + 1) + 130;
            Integer zbrojStatistikaNapadaca = 0;


            for (Integer j = 0; j < brojNapada; j++) {
                if (posjed == 1) {
                    for (Student student : pocetnaPetorkaJedan) {
                        zbrojStatistikaNapadaca += mapaStudStat.get(student).getSutZaDva() + mapaStudStat.get(student).getSutZaTri();
                    }
                    Integer brojKojiOdlucujeNapadaca = random.nextInt(zbrojStatistikaNapadaca + 1);
                    Integer brojac = 0;
                    for (Student student : pocetnaPetorkaJedan) {
                        brojac += mapaStudStat.get(student).getSutZaTri() + mapaStudStat.get(student).getSutZaDva();
                        if (brojac >= brojKojiOdlucujeNapadaca) {
                            napadac = student;
                            asistent = napadac;
                            while (asistent.equals(napadac)) {
                                String pozicija = tkoAsistira();
                                asistent = pocetnaPetorkaJedan.stream().filter(igrac -> igrac.getPozicija().equals(pozicija)).findFirst().get();
                            }
                            zbrojStatistikaNapadaca = 0;
                            break;
                        }
                    }
                    Student finalNapadac = napadac;
                    Optional<Student> obrambeniOpt = pocetnaPetorkaDva.stream().filter(igrac -> igrac.getPozicija().equals(finalNapadac.getPozicija())).findFirst();
                    Student obrambeni = obrambeniOpt.get();
                    napad(napadac, obrambeni, asistent);
                } else {
                    for (Student student : pocetnaPetorkaDva) {
                        zbrojStatistikaNapadaca += mapaStudStat.get(student).getSutZaDva() + mapaStudStat.get(student).getSutZaTri();
                    }
                    Integer brojKojiOdlucujeNapadaca = random.nextInt(zbrojStatistikaNapadaca + 1);
                    Integer brojac = 0;
                    for (Student student : pocetnaPetorkaDva) {
                        brojac += mapaStudStat.get(student).getSutZaTri() + mapaStudStat.get(student).getSutZaDva();
                        if (brojac >= brojKojiOdlucujeNapadaca) {
                            napadac = student;
                            asistent = napadac;
                            while (asistent.equals(napadac)) {
                                String pozicija = tkoAsistira();
                                asistent = pocetnaPetorkaDva.stream().filter(igrac -> igrac.getPozicija().equals(pozicija)).findFirst().get();
                            }
                            zbrojStatistikaNapadaca = 0;
                            break;
                        }
                    }
                    Student finalNapadac = napadac;
                    Optional<Student> obrambeniOpt = pocetnaPetorkaJedan.stream().filter(igrac -> igrac.getPozicija().equals(finalNapadac.getPozicija())).findFirst();
                    Student obrambeni = obrambeniOpt.get();
                    System.out.println("NAPAD " + napadac);
                    System.out.println("OBRANA" + obrambeni);
                    napad(napadac, obrambeni, asistent);
                }
            }

            System.out.println(boxScoreSvi);


            BazaPodataka.unesiNovuUtakmicu(utakmica);
            Integer zadnjaUtakmica = BazaPodataka.dohvatiIdZadnjeUtakmice();

            boxScoreSvi.stream().forEach(boxScore -> {
                Integer boxScoreId = 0;
                BazaPodataka.unesiNoviBoxScore(boxScore);
                try {
                    boxScoreId = BazaPodataka.dohvatiZadnjiBoxScoreId();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Integer vrijednost = izracunajVrijednostUtakmice(boxScore);
                Performans noviPerformans = new Performans(zadnjaUtakmica, boxScore.getIdStudenta(), boxScoreId, vrijednost);
                BazaPodataka.unesiNoviPerformans(noviPerformans);
                performansiLista.add(noviPerformans);
            });
            dodajBodoveKorisnicima();
            for(BoxScore boxScore:boxScoreSvi) {
                Student student = BazaPodataka.dohvatiStudentaPremaId(boxScore.getIdStudenta());
                if (student.getFakultetId().equals( fakultet1.get().getId())) {
                    brojKosevaDomaci += boxScore.getKosevi();
                } else {
                    brojKosevaGosti += boxScore.getKosevi();
                }
            }
            Rezultat rezultat=new Rezultat(zadnjaUtakmica,brojKosevaDomaci,brojKosevaGosti);
            BazaPodataka.unesiNoviRezultat(rezultat);


            brojKosevaGosti=0;
            brojKosevaDomaci=0;
            performansiLista.clear();
            boxScoreSvi.clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void napad(Student napadac, Student obrambeni, Student asistent) {
        try {
            Stats statistikeNapadac = BazaPodataka.dohvatiStatsStudenta(napadac.getId());
            Stats statistikeObrambeni = BazaPodataka.dohvatiStatsStudenta(obrambeni.getId());
            Random random = new Random();

            Integer vjestinaZaKradu = statistikeNapadac.getDribling() - statistikeObrambeni.getUkradene();
            vjestinaZaKradu += 100;
            Integer ukradena = random.nextInt(vjestinaZaKradu + 1);
            if (ukradena <= 8) {
                System.out.println("UKRADENA LOPTA");
                boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals( obrambeni.getId())).findFirst()
                        .map(boxScore -> {
                            boxScore.setUkradene(boxScore.getUkradene() + 1);
                            return boxScore;
                        }).orElse(null);
                boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals(napadac.getId())).findFirst()
                        .map(boxScore -> {
                            boxScore.setIzgubljene(boxScore.getIzgubljene() + 1);
                            return boxScore;
                        }).orElse(null);
                promijeniPosjed();
                return;
            }
            Integer upuceniSut;
            Integer triIliDva = statistikeNapadac.getSutZaDva() + statistikeNapadac.getSutZaTri();
            Integer randomTriIliDva = random.nextInt(triIliDva + 1);
            if (randomTriIliDva > statistikeNapadac.getSutZaDva()) {
                upuceniSut = 3;
            } else {
                upuceniSut = 2;
            }

            Integer razlikaUVisini = statistikeNapadac.getVisina() - statistikeObrambeni.getVisina();

            if (upuceniSut == 2) {
                Integer vjestinaZaDva = (statistikeNapadac.getSutZaDva() - statistikeObrambeni.getBlok()) + 100;
                Integer zabijenIliFulan = random.nextInt(vjestinaZaDva + 1);
                if (zabijenIliFulan <= 55) {
                    System.out.println("ZABIJEN SUT ZA 2");
                    boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals( napadac.getId())).findFirst()
                            .map(boxScore -> {
                                boxScore.setZabijeneDvice(boxScore.getZabijeneDvice() + 1);
                                boxScore.setOpucaneDvice(boxScore.getOpucaneDvice() + 1);
                                boxScore.setKosevi(boxScore.getKosevi() + 2);
                                return boxScore;
                            }).orElse(null);

                    if (random.nextInt(100 + 1) >= 41) {
                        boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals( asistent.getId())).findFirst()
                                .map(boxScore -> {
                                    boxScore.setAsistencije(boxScore.getAsistencije() + 1);
                                    return boxScore;
                                }).orElse(null);
                    }
                    if (random.nextInt(100 + 1 + statistikeObrambeni.getObranaInt()) <= 15) {
                        boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals(obrambeni.getId())).findFirst()
                                .map(boxScore -> {
                                    boxScore.setFauli(boxScore.getFauli() + 1);
                                    return boxScore;
                                }).orElse(null);
                        if (random.nextInt(100 + 1) + (random.nextInt(10 + 1) + 5) <= statistikeNapadac.getSutZaDva()) {
                            boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals( napadac.getId())).findFirst()
                                    .map(boxScore -> {
                                        boxScore.setKosevi(boxScore.getKosevi() + 1);
                                        boxScore.setOpucanaSlobodna(boxScore.getOpucanaSlobodna() + 1);
                                        boxScore.setZabijenaSlobodna(boxScore.getZabijenaSlobodna()+1);
                                        return boxScore;
                                    }).orElse(null);
                        } else {
                            boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals( napadac.getId())).findFirst()
                                    .map(boxScore -> {
                                        boxScore.setOpucanaSlobodna(boxScore.getOpucanaSlobodna() + 1);
                                        return boxScore;
                                    }).orElse(null);
                            if (posjed == 1) {
                                skok(pocetnaPetorkaJedan, pocetnaPetorkaDva);
                            } else {
                                skok(pocetnaPetorkaDva, pocetnaPetorkaJedan);
                            }
                        }
                    } else {
                        promijeniPosjed();
                    }

                } else if (zabijenIliFulan <= 65 + razlikaUVisini / 3) {
                    System.out.println("BLOKIRAN SUT ZA 2");
                    boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals( napadac.getId())).findFirst()
                            .map(boxScore -> {
                                boxScore.setOpucaneDvice(boxScore.getOpucaneDvice() + 1);
                                return boxScore;
                            }).orElse(null);
                    boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals(obrambeni.getId())).findFirst()
                            .map(boxScore -> {
                                boxScore.setBlokovi(boxScore.getBlokovi() + 1);
                                return boxScore;
                            }).orElse(null);

                    promijeniPosjed();

                } else {
                    System.out.println("PROMASEN SUT ZA 2");
                    if (random.nextInt(100 + 1 + statistikeObrambeni.getObranaInt()) <= 35) {
                        boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals(obrambeni.getId())).findFirst()
                                .map(boxScore -> {
                                    boxScore.setFauli(boxScore.getFauli() + 1);
                                    return boxScore;
                                }).orElse(null);
                        Integer zabijeno = 0;
                        for (Integer i = 0; i < 2; i++) {
                            if (random.nextInt(100 + 1) - (random.nextInt(10 + 1) + 5) <= statistikeNapadac.getSutZaDva()) {
                                boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals(napadac.getId())).findFirst()
                                        .map(boxScore -> {
                                            boxScore.setKosevi(boxScore.getKosevi() + 1);
                                            boxScore.setOpucanaSlobodna(boxScore.getOpucanaSlobodna() + 1);
                                            return boxScore;
                                        }).orElse(null);
                                zabijeno = i+1;
                            } else {
                                boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals(napadac.getId())).findFirst()
                                        .map(boxScore -> {
                                            boxScore.setOpucanaSlobodna(boxScore.getOpucanaSlobodna() + 1);
                                            return boxScore;
                                        }).orElse(null);
                            }

                        }
                        if (zabijeno == 2) {
                            if (posjed == 1) {
                                skok(pocetnaPetorkaJedan, pocetnaPetorkaDva);
                            } else {
                                skok(pocetnaPetorkaDva, pocetnaPetorkaJedan);
                            }
                        }
                    } else {
                        boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals(napadac.getId())).findFirst()
                                .map(boxScore -> {
                                    boxScore.setOpucaneDvice(boxScore.getOpucaneDvice() + 1);
                                    return boxScore;
                                }).orElse(null);
                        if (posjed == 1) {
                            skok(pocetnaPetorkaJedan, pocetnaPetorkaDva);
                        } else {
                            skok(pocetnaPetorkaDva, pocetnaPetorkaJedan);
                        }
                    }
                }
            } else if (upuceniSut == 3) {
                Integer vjestinaZaTri = (statistikeNapadac.getSutZaTri() - statistikeObrambeni.getObranaPerim()) + 100;
                Integer zabijenIliFulan = random.nextInt(vjestinaZaTri + 1);
                if (zabijenIliFulan <= 40) {
                    System.out.println("ZABIJEN SUT ZA TRI");
                    boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals(napadac.getId())).findFirst()
                            .map(boxScore -> {
                                boxScore.setZabijeneTrice(boxScore.getZabijeneTrice() + 1);
                                boxScore.setOpucaneTrice(boxScore.getOpucaneTrice() + 1);
                                boxScore.setKosevi(boxScore.getKosevi() + 3);
                                return boxScore;
                            }).orElse(null);
                    if (random.nextInt(100 + 1) >= 41) {
                        boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals(asistent.getId())).findFirst()
                                .map(boxScore -> {
                                    boxScore.setAsistencije(boxScore.getAsistencije() + 1);
                                    return boxScore;
                                }).orElse(null);
                    }

                    if (random.nextInt(100 + 1 + statistikeObrambeni.getObranaPerim()) <= 3) {
                        boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals(obrambeni.getId())).findFirst()
                                .map(boxScore -> {
                                    boxScore.setFauli(boxScore.getFauli() + 1);
                                    return boxScore;
                                }).orElse(null);
                        if (random.nextInt(100 + 1) - (random.nextInt(10 + 1) + 5) <= statistikeNapadac.getSutZaDva()) {
                            boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals( napadac.getId())).findFirst()
                                    .map(boxScore -> {
                                        boxScore.setKosevi(boxScore.getKosevi() + 1);
                                        boxScore.setOpucanaSlobodna(boxScore.getOpucanaSlobodna() + 1);
                                        return boxScore;
                                    }).orElse(null);
                        } else {
                            boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals(napadac.getId())).findFirst()
                                    .map(boxScore -> {
                                        boxScore.setOpucanaSlobodna(boxScore.getOpucanaSlobodna() + 1);
                                        return boxScore;
                                    }).orElse(null);
                            if (posjed == 1) {
                                skok(pocetnaPetorkaJedan, pocetnaPetorkaDva);
                            } else {
                                skok(pocetnaPetorkaDva, pocetnaPetorkaJedan);
                            }
                        }
                    } else {
                        promijeniPosjed();
                    }
                } else {
                    System.out.println("PROMASEN SUT ZA TRI");
                    boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals( napadac.getId())).findFirst()
                            .map(boxScore -> {
                                boxScore.setOpucaneTrice(boxScore.getOpucaneTrice() + 1);
                                return boxScore;
                            }).orElse(null);


                    if (random.nextInt(100 + 1) + statistikeObrambeni.getObranaPerim() <= 10) {
                        boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals(obrambeni.getId())).findFirst()
                                .map(boxScore -> {
                                    boxScore.setFauli(boxScore.getFauli() + 1);
                                    return boxScore;
                                }).orElse(null);
                        Integer zabijeno = 0;
                        for (Integer i = 0; i < 3; i++) {
                            if (random.nextInt(100 + 1) - (random.nextInt(10 + 1) + 5) <= statistikeNapadac.getSutZaDva()) {
                                boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals(napadac.getId())).findFirst()
                                        .map(boxScore -> {
                                            boxScore.setKosevi(boxScore.getKosevi() + 1);
                                            boxScore.setOpucanaSlobodna(boxScore.getOpucanaSlobodna() + 1);
                                            return boxScore;
                                        }).orElse(null);
                                zabijeno = 1;
                            } else {
                                boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals(napadac.getId())).findFirst()
                                        .map(boxScore -> {
                                            boxScore.setOpucanaSlobodna(boxScore.getOpucanaSlobodna() + 1);
                                            return boxScore;
                                        }).orElse(null);
                                zabijeno = 0;
                            }

                        }
                        if (zabijeno == 2) {
                            if (posjed == 1) {
                                skok(pocetnaPetorkaJedan, pocetnaPetorkaDva);
                            } else {
                                skok(pocetnaPetorkaDva, pocetnaPetorkaJedan);
                            }
                        }
                    } else {
                        if (posjed == 1) {
                            skok(pocetnaPetorkaJedan, pocetnaPetorkaDva);
                        } else {
                            skok(pocetnaPetorkaDva, pocetnaPetorkaJedan);
                        }
                    }
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void skok(List<Student> petorkaNapad, List<Student> petorkaObrana) {
        String pozicija = tkoSkace();
        Optional<Student> prvi = petorkaObrana.stream().filter(student -> student.getPozicija().equals(pozicija)).findFirst();
        Optional<Student> drugi = petorkaNapad.stream().filter(student -> student.getPozicija().equals(pozicija)).findFirst();

        Student obrambeniIgrac = prvi.get();
        Student ofenzivniIgrac = drugi.get();

        try {
            Stats statistikeObrana = BazaPodataka.dohvatiStatsStudenta(obrambeniIgrac.getId());
            Stats statistikeNapad = BazaPodataka.dohvatiStatsStudenta(ofenzivniIgrac.getId());
            Random random = new Random();

            Integer prednostPriSkoku = (statistikeObrana.getVisina() + statistikeObrana.getKilaza() +
                    statistikeObrana.getSkok()) - (statistikeNapad.getVisina() + statistikeNapad.getKilaza() + statistikeObrana.getSkok());
            Integer tkoOsvaja = random.nextInt(99 + 1 + prednostPriSkoku);
            if (tkoOsvaja >= 30) {
                promijeniPosjed();
                boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals(obrambeniIgrac.getId())).findFirst()
                        .map(boxScore -> {
                            boxScore.setSkokovi(boxScore.getSkokovi() + 1);
                            return boxScore;
                        }).orElse(null);
            } else {
                boxScoreSvi.stream().filter(boxScore -> boxScore.getIdStudenta().equals(ofenzivniIgrac.getId())).findFirst()
                        .map(boxScore -> {
                            boxScore.setSkokovi(boxScore.getSkokovi() + 1);
                            return boxScore;
                        }).orElse(null);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public String tkoSkace() {
        Random random = new Random();
        Integer brojPozicije = random.nextInt(99 + 1);

        if (brojPozicije <= 30) {
            return "C";
        } else if (brojPozicije <= 55) {
            return "PF";
        } else if (brojPozicije <= 75) {
            return "SF";
        } else if (brojPozicije <= 90) {
            return "SG";
        } else {
            return "PG";
        }
    }

    public String tkoAsistira() {
        Random random = new Random();
        Integer brojPozicije = random.nextInt(99 + 1);

        if (brojPozicije <= 30) {
            return "PG";
        } else if (brojPozicije <= 55) {
            return "SG";
        } else if (brojPozicije <= 75) {
            return "SF";
        } else if (brojPozicije <= 90) {
            return "PF";
        } else {
            return "C";
        }
    }

    public void promijeniPosjed() {
        System.out.println("PROMJENJEN POSJED");
        if (posjed == 1) {
            posjed = 2;
        } else {
            posjed = 1;
        }
    }

    public Integer izracunajVrijednostUtakmice(BoxScore boxScore) {
        Integer bodovi = 0;

        if (boxScore.getOpucanaSlobodna() + boxScore.getOpucaneDvice() + boxScore.getOpucaneTrice() != 0) {
            bodovi = (boxScore.getKosevi() * 10) + (boxScore.getSkokovi() * 12) + (boxScore.getAsistencije() * 15) + (boxScore.getUkradene() * 30) +
                    (boxScore.getBlokovi() * 30) - (boxScore.getIzgubljene() * 15) - (boxScore.getFauli() * 10) +
                    ((boxScore.getZabijenaSlobodna() + boxScore.getZabijeneDvice() + boxScore.getZabijeneTrice()) /
                            (boxScore.getOpucanaSlobodna() + boxScore.getOpucaneDvice() + boxScore.getOpucaneTrice()));
        } else {
            bodovi = (boxScore.getKosevi() * 10) + (boxScore.getSkokovi() * 12) + (boxScore.getAsistencije() * 15) + (boxScore.getUkradene() * 30) +
                    (boxScore.getBlokovi() * 30) - (boxScore.getIzgubljene() * 15) - (boxScore.getFauli() * 10);
        }
        return bodovi;
    }

    public void dodajBodoveKorisnicima() {
       for (Korisnik korisnik:korisnici) {
           try {
               List<Liga> lige=BazaPodataka.dohvatiSveLige();
               for(Liga liga:lige) {
                   igraciKorisnika = BazaPodataka.dohvatiIgraceKorisnika(korisnik.getId(), liga.getId());

           for (Performans performans : performansiLista) {
               System.out.println(igraciKorisnika);
               igraciKorisnika.stream().forEach(igracKorisnik -> {
                   if (igracKorisnik.getIgracId().equals(performans.getIdStudent())) {
                       try {
                           Optional<BodoviLiga> bodoviLiga;
                           bodoviLiga=BazaPodataka.dohvatiBodoveKorisnikaLige(korisnik.getId(), liga.getId());
                           if(!bodoviLiga.isEmpty()) {
                               BazaPodataka.promjeniBodove(bodoviLiga.get(), performans.getBodovi());
                           }
                           else {
                               bodoviLiga= Optional.of(new BodoviLiga(korisnik.getId(), liga.getId(), (double) performans.getBodovi()));
                                BazaPodataka.unesiNoveBodoveLige(bodoviLiga.get());
                           }
                       } catch (SQLException e) {
                           System.out.println("GRESKA U PROMJENI");
                           throw new RuntimeException(e);
                       } catch (IOException e) {
                           System.out.println("GRESKA U PROMJENI");
                           throw new RuntimeException(e);
                       }
                   }

               });
           }
               }
           } catch (SQLException e) {
               throw new RuntimeException(e);
           } catch (IOException e) {
               throw new RuntimeException(e);
           }}
    }
    public void otvoriOdabirPetorke(){
        try {
            IzbornikAdminController.pokreniAdmina();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}