package baza;

import entiteti.*;
import hr.tvz.pejkunovic.demo.OdabirPetorkeController;
import javafx.scene.shape.Box;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BazaPodataka {
    private static Logger logger = LoggerFactory.getLogger(BazaPodataka.class);

    private static Connection spojiSeSBazom() throws SQLException, IOException {

        Properties configuration = new Properties();
        configuration.load(new FileReader("C:\\Users\\fpejk\\Desktop\\Zavrsni\\demo\\dat\\database.properties"));

        String databaseURL = configuration.getProperty("databaseURL");
        String databaseUsername = configuration.getProperty("databaseUsername");
        String databasePassword = configuration.getProperty("databasePassword");

       /* logger.info(databaseURL);
        logger.info(databaseUsername);
        logger.info(databasePassword);
*/

        Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
        return connection;
    }

    public static List<Student> dohvatiSveStudente() throws SQLException, IOException {

        Connection connection = spojiSeSBazom();

        List<Student> studentiLista = new ArrayList<>();

        Statement sqlStatement = connection.createStatement();

        ResultSet studentResultSet = sqlStatement.executeQuery("SELECT * FROM student");

        while (studentResultSet.next()) {
            Student noviStudent = getStudentFromResultSet(studentResultSet);
            studentiLista.add(noviStudent);
        }

        connection.close();

        return studentiLista;
    }

    private static Student getStudentFromResultSet(ResultSet studentResultSet) throws SQLException {
        Integer id = studentResultSet.getInt("ID");
        String ime = studentResultSet.getString("IME");
        String prezime = studentResultSet.getString("PREZIME");
        String pozicija = studentResultSet.getString("POZICIJA");
        Integer fakultetId = studentResultSet.getInt("FAKULTET_ID");
        LocalDate datumRodenja = studentResultSet.getDate("DATUM_RODENJA").toLocalDate();

        return new Student(id, ime, prezime, fakultetId, pozicija, datumRodenja);
    }

    public static List<Student> dohvatiSlobodneStudente(Integer idLiga) throws SQLException {
        List<Student> listaStudenta = new ArrayList<>();
        try (Connection veza = spojiSeSBazom()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "Select * from student where id not in (select id_student from igrac_korisnik where id_liga= "+idLiga+" )");
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String ime = resultSet.getString("ime");
                String prezime = resultSet.getString("prezime");
                String pozicija = resultSet.getString("pozicija");
                LocalDate datum = resultSet.getDate("datum_rodenja").toLocalDate();
                Integer fakultetId = resultSet.getInt("fakultet_id");


                Student noviStudent = new Student(id, ime, prezime, fakultetId, pozicija, datum);
                listaStudenta.add(noviStudent);
                System.out.println("STUDENT " + listaStudenta.toString());
            }
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return listaStudenta;
    }

    public static void unesiNovogKorisnika(Korisnik korisnik){
        try (Connection veza = spojiSeSBazom()) {
            PreparedStatement preparedStatement = veza
                    .prepareStatement(
                            "INSERT INTO KORISNIK(USERNAME,PASSWORD,ULOGA_ID,BODOVI) VALUES (?,?,?,?)");
            preparedStatement.setString(1, korisnik.getUsername());
            preparedStatement.setString(2, korisnik.getPassword());
            preparedStatement.setInt(3, korisnik.getRolaId());
            preparedStatement.setDouble(4, korisnik.getBodovi());

            preparedStatement.executeUpdate();
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
        }
    }
    public static List<Student> dohvatiStudentePremaKriterijima(Student student,Integer idLiga) throws SQLException {
        List<Student> listaStudenta = new ArrayList<>();
        logger.info(student.getPozicija());
        //Kljuc kljuc=new Kljuc();
        try (Connection veza = spojiSeSBazom()) {
            /*do{
                kljuc=dohvatiKljuc();
            }while (kljuc.getZauzet()==1);
            kljuc.setZauzet(1);
            promjeniKljuc(kljuc);*/
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM STUDENT WHERE 1 = 1 AND ID NOT IN(select id_student from igrac_korisnik where id_liga= "+idLiga+" )");
            if (Optional.ofNullable(student).isEmpty() == false) {
                if (Optional.ofNullable(student.getIme()).map(String::isBlank).orElse(true) == false) {
                    sqlUpit.append(" AND (UPPER(IME) LIKE UPPER('%" +
                            student.getIme() + "%')");
                }
                if (Optional.ofNullable(student.getIme()).map(String::isBlank).orElse(true) == false) {
                    sqlUpit.append(" OR UPPER(PREZIME) LIKE UPPER('%" +
                            student.getIme() + "%'))");
                }
                if (student.getFakultetId() != 0) {
                    sqlUpit.append(" AND FAKULTET_ID = " +
                            student.getFakultetId());
                }
                if (Optional.ofNullable(student.getPozicija()).map(String::isBlank).orElse(true) == false) {

                    sqlUpit.append(" AND UPPER(POZICIJA) = UPPER('" +
                            student.getPozicija() + "')");
                }
            }
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String ime = resultSet.getString("ime");
                String prezime = resultSet.getString("prezime");
                String pozicija = resultSet.getString("pozicija");
                LocalDate datum = resultSet.getDate("datum_rodenja").toLocalDate();
                Integer fakultetId = resultSet.getInt("fakultet_id");


                Student noviStudent = new Student(id, ime, prezime, fakultetId, pozicija, datum);
                listaStudenta.add(noviStudent);
                System.out.println("STUDENT " + listaStudenta.toString());
            }
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }

        /*kljuc.setZauzet(0);
        promjeniKljuc(kljuc);*/
        return listaStudenta;
    }

    public static List<Korisnik> dohvatiSveKorisnike() throws SQLException, IOException {

        Connection connection = spojiSeSBazom();

        List<Korisnik> korisniciLista = new ArrayList<>();

        Statement sqlStatement = connection.createStatement();

        ResultSet korisnikResultSet = sqlStatement.executeQuery("SELECT * FROM korisnik");

        while (korisnikResultSet.next()) {
            Korisnik noviKorisnik = getKorisnikFromResultSet(korisnikResultSet);
            korisniciLista.add(noviKorisnik);
        }

        connection.close();

        return korisniciLista;
    }

    private static Korisnik getKorisnikFromResultSet(ResultSet korisnikResultSet) throws SQLException {
        Integer id = korisnikResultSet.getInt("ID");
        String username = korisnikResultSet.getString("USERNAME");
        String password = korisnikResultSet.getString("PASSWORD");
        Integer pozicija = korisnikResultSet.getInt("ULOGA_ID");
        Double bodovi=korisnikResultSet.getDouble("BODOVI");

        System.out.println("Dohvacen korisnik");
        return new Korisnik(id, username, password, pozicija,bodovi);
    }


    public static Korisnik dohvatiKorisnikaPremaUsernameu(String username) throws SQLException {
        Korisnik korisnik = new Korisnik();
        try (Connection veza = spojiSeSBazom()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM KORISNIK WHERE 1 = 1");
            if (username != "") {
                sqlUpit.append(" AND USERNAME =  '" + username + "'");
            }
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                System.out.println("Krenuo s bazom");
                Integer idNew = resultSet.getInt("id");
                String usernameNew = resultSet.getString("username");
                String passwordNew = resultSet.getString("password");
                Integer ulogaIdNew = resultSet.getInt("uloga_id");
                Double bodovi=resultSet.getDouble("bodovi");


                korisnik = new Korisnik(idNew, usernameNew, passwordNew, ulogaIdNew,bodovi);
            }
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return korisnik;
    }


    public static Korisnik dohvatiKorisnikaPremaId(Integer id) throws SQLException {
        Korisnik korisnik = new Korisnik();
        try (Connection veza = spojiSeSBazom()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM KORISNIK WHERE 1 = 1");
                sqlUpit.append(" AND ID =  '" + id + "'");
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                System.out.println("Krenuo s bazom");
                Integer idNew = resultSet.getInt("id");
                String usernameNew = resultSet.getString("username");
                String passwordNew = resultSet.getString("password");
                Integer ulogaIdNew = resultSet.getInt("uloga_id");
                Double bodovi=resultSet.getDouble("bodovi");

                korisnik = new Korisnik(idNew, usernameNew, passwordNew, ulogaIdNew,bodovi);
            }
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return korisnik;
    }


    public static void promjeniKorisnik(Korisnik korisnik) throws SQLException, IOException {
        Connection connection = spojiSeSBazom();
            PreparedStatement updateKorisnik =
                    connection.prepareStatement(
                            "UPDATE KORISNIK SET BODOVI = ?WHERE ID = ?");
            updateKorisnik.setDouble(1, korisnik.getBodovi());
            updateKorisnik.setInt(2, korisnik.getId());
            updateKorisnik.executeUpdate();

        connection.close();
    }

    public static Student dohvatiStudentaPremaId(Integer id) throws SQLException {
        Student student = new Student();
        try (Connection veza = spojiSeSBazom()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM STUDENT WHERE 1 = 1");
            if (id != 0) {
                sqlUpit.append(" AND ID =  " + id);
            }
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                System.out.println("Krenuo s bazom");
                Integer idNew = resultSet.getInt("id");
                String imeNew = resultSet.getString("ime");
                String prezimeNew = resultSet.getString("prezime");
                Integer fakultetIdNew = resultSet.getInt("fakultet_id");
                String pozicijaNew = resultSet.getString("pozicija");
                LocalDate datumRodenjaNew = resultSet.getDate("datum_rodenja").toLocalDate();

                student = new Student(idNew, imeNew, prezimeNew, fakultetIdNew, pozicijaNew, datumRodenjaNew);
            }
        } catch (SQLException | IOException ex) {
            System.out.println("Greska u dohvatiStudentaPremaId");
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return student;
    }

    public static List<IgracKorisnik> dohvatiIgraceKorisnika(Integer korisnikId,Integer ligaId) throws SQLException {
        List<IgracKorisnik> listaIgracaKorisnika = new ArrayList<>();
        try (Connection veza = spojiSeSBazom()) {
            IgracKorisnik noviIgracKorisnik = new IgracKorisnik();
            System.out.println(korisnikId);
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM IGRAC_KORISNIK WHERE 1 = 1");
            if (korisnikId != 0) {
                sqlUpit.append("AND ID_KORISNIK =  " + korisnikId);
                sqlUpit.append("AND ID_LIGA =  " + ligaId);
            }
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            System.out.println("Upit Proso");
            while (resultSet.next()) {
                System.out.println("Krenuo s bazom");
                Integer idNew = resultSet.getInt("id");
                Integer idKorisnikNew = resultSet.getInt("id_korisnik");
                Integer idStudentNew = resultSet.getInt("id_student");
                Integer idLiga=resultSet.getInt("id_liga");


                noviIgracKorisnik = new IgracKorisnik(idNew, idKorisnikNew, idStudentNew,idLiga);
                listaIgracaKorisnika.add(noviIgracKorisnik);
            }
        } catch (SQLException | IOException ex) {
            System.out.println("Greska u dohvatiIgraceKorisnika");
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return listaIgracaKorisnika;
    }


    public static List<IgracKorisnik> dohvatiIgracKorisnikaPoLigi(Integer ligaId) throws SQLException {
        List<IgracKorisnik> listaIgracaKorisnika = new ArrayList<>();
        try (Connection veza = spojiSeSBazom()) {
            IgracKorisnik noviIgracKorisnik = new IgracKorisnik();
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM IGRAC_KORISNIK WHERE 1 = 1");
                sqlUpit.append("AND ID_LIGA =  " + ligaId);
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            System.out.println("Upit Proso");
            while (resultSet.next()) {
                System.out.println("Krenuo s bazom");
                Integer idNew = resultSet.getInt("id");
                Integer idKorisnikNew = resultSet.getInt("id_korisnik");
                Integer idStudentNew = resultSet.getInt("id_student");
                Integer idLiga=resultSet.getInt("id_liga");


                noviIgracKorisnik = new IgracKorisnik(idNew, idKorisnikNew, idStudentNew,idLiga);
                listaIgracaKorisnika.add(noviIgracKorisnik);
            }
        } catch (SQLException | IOException ex) {
            System.out.println("Greska u dohvatiIgraceKorisnika");
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return listaIgracaKorisnika;
    }

    public static void dodajStudentaUPetorku(Integer studentId, Integer korisnikId,Integer ligaId) throws SQLException {
        try (Connection veza = spojiSeSBazom()) {
            PreparedStatement preparedStatement = veza
                    .prepareStatement(
                            "INSERT INTO IGRAC_KORISNIK(ID_KORISNIK, ID_STUDENT,ID_LIGA) VALUES (?, ?, ?)");
            preparedStatement.setInt(1, korisnikId);
            preparedStatement.setInt(2, studentId);
            preparedStatement.setInt(3, ligaId);

            preparedStatement.executeUpdate();
        } catch (SQLException | IOException ex) {
            System.out.println("Greska u dodajIgraceKorisnika");
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
    }

    public static void makniStudentaIzPetorke(Integer studentId, Integer korisnikId) throws SQLException, IOException {
        Connection connection = spojiSeSBazom();
        PreparedStatement makniStudenta =
                connection.prepareStatement(
                        "DELETE FROM IGRAC_KORISNIK WHERE ID_KORISNIK = ? AND ID_STUDENT = ?");
        makniStudenta.setInt(1, korisnikId);
        makniStudenta.setInt(2, studentId);
        makniStudenta.executeUpdate();

        connection.close();
    }

    public static List<Fakultet> dohvatiSveFakultete() throws SQLException, IOException {

        Connection connection = spojiSeSBazom();

        List<Fakultet> fakultetiLista = new ArrayList<>();

        Statement sqlStatement = connection.createStatement();

        ResultSet fakultetResultSet = sqlStatement.executeQuery("SELECT * FROM fakultet");

        while (fakultetResultSet.next()) {
            Fakultet noviFakultet = getFakultetFromResultSet(fakultetResultSet);
            fakultetiLista.add(noviFakultet);
        }

        connection.close();

        return fakultetiLista;
    }

    private static Fakultet getFakultetFromResultSet(ResultSet fakultetResultSet) throws SQLException {
        Integer id = fakultetResultSet.getInt("ID");
        String naziv = fakultetResultSet.getString("NAZIV");

        return new Fakultet(id, naziv);
    }

    public static Fakultet dohvatiFakultetePremaIdu(Integer id) throws SQLException {
        Fakultet noviFakultet = new Fakultet();
        try (Connection veza = spojiSeSBazom()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM FAKULTET WHERE 1 = 1");
            sqlUpit.append(" AND ID =" + id);
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Integer idNew = resultSet.getInt("id");
                String nazivNew = resultSet.getString("naziv");

                noviFakultet = new Fakultet(idNew, nazivNew);
            }
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return noviFakultet;
    }

    public static List<Fakultet> dohvatiFakultetePremaImenu(String filter) throws SQLException {
        Fakultet noviFakultet = new Fakultet();
        List<Fakultet> listaFakulteta = new ArrayList<>();
        try (Connection veza = spojiSeSBazom()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM FAKULTET WHERE 1 = 1");
            if (Optional.ofNullable(filter).map(String::isBlank).orElse(true) == false)
                sqlUpit.append(" AND UPPER(NAZIV) LIKE UPPER('%" + filter + "%')");
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Integer idNew = resultSet.getInt("id");
                String nazivNew = resultSet.getString("naziv");

                noviFakultet = new Fakultet(idNew, nazivNew);
                listaFakulteta.add(noviFakultet);
            }
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return listaFakulteta;
    }

    public static List<Student> dohvatiStudenteSFakulteta(Integer idFakulteta) throws SQLException {
        List<Student> listaStudenta = new ArrayList<>();
        try (Connection veza = spojiSeSBazom()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM STUDENT WHERE 1 = 1");
            sqlUpit.append(" AND FAKULTET_ID = " +
                    idFakulteta);

            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String ime = resultSet.getString("ime");
                String prezime = resultSet.getString("prezime");
                String pozicija = resultSet.getString("pozicija");
                LocalDate datum = resultSet.getDate("datum_rodenja").toLocalDate();
                Integer fakultetId = resultSet.getInt("fakultet_id");


                Student noviStudent = new Student(id, ime, prezime, fakultetId, pozicija, datum);
                listaStudenta.add(noviStudent);
            }
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return listaStudenta;
    }


    private static Stats getStatsFromResultSet(ResultSet studentResultSet) throws SQLException {
        Integer id = studentResultSet.getInt("ID");
        Integer idStudenta = studentResultSet.getInt("ID_STUDENT");
        Integer sutZaDva = studentResultSet.getInt("SUT_ZA_DVA");
        Integer sutZaTri = studentResultSet.getInt("SUT_ZA_TRI");
        Integer obranaPerim = studentResultSet.getInt("OBRANA_PERIM");
        Integer obranaInt = studentResultSet.getInt("OBRANA_INT");
        Integer skok = studentResultSet.getInt("SKOK");
        Integer visina = studentResultSet.getInt("VISINA");
        Integer kilaza = studentResultSet.getInt("KILAZA");
        Integer dribling = studentResultSet.getInt("DRIBLING");
        Integer ukradene = studentResultSet.getInt("UKRADENE");
        Integer blok = studentResultSet.getInt("BLOK");


        return new Stats(id, idStudenta, sutZaDva, sutZaTri, obranaPerim, obranaInt, skok, visina, kilaza, dribling, ukradene, blok);
    }

    public static Stats dohvatiStatsStudenta(Integer studentId) throws SQLException {
        Stats noviStat = new Stats();
        List<Student> listaStudenta = new ArrayList<>();
        try (Connection veza = spojiSeSBazom()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM STUDENT_STATS WHERE 1 = 1");

            sqlUpit.append(" AND ID_STUDENT = '" +
                    studentId + "'");
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer idStudenta = resultSet.getInt("ID_STUDENT");
                Integer sutZaDva = resultSet.getInt("SUT_ZA_DVA");
                Integer sutZaTri = resultSet.getInt("SUT_ZA_TRI");
                Integer obranaPerim = resultSet.getInt("OBRANA_PERIM");
                Integer obranaInt = resultSet.getInt("OBRANA_INT");
                Integer skok = resultSet.getInt("SKOK");
                Integer visina = resultSet.getInt("VISINA");
                Integer kilaza = resultSet.getInt("KILAZA");
                Integer dribling = resultSet.getInt("DRIBLING");
                Integer ukradene = resultSet.getInt("UKRADENE");
                Integer blok = resultSet.getInt("BLOK");


                noviStat = new Stats(id, idStudenta, sutZaDva, sutZaTri, obranaPerim, obranaInt, skok, visina, kilaza, dribling, ukradene, blok);

            }
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return noviStat;
    }

    public static List<BoxScore> dohvatiSveBoxScore() throws SQLException, IOException {

        Connection connection = spojiSeSBazom();

        List<BoxScore> boxScoreLista = new ArrayList<>();

        Statement sqlStatement = connection.createStatement();

        ResultSet boxScoreResultSet = sqlStatement.executeQuery("SELECT * FROM boxscore");

        while (boxScoreResultSet.next()) {
            BoxScore noviBoxScore = getBoxScoreFromResultSet(boxScoreResultSet);
            boxScoreLista.add(noviBoxScore);
        }

        connection.close();

        return boxScoreLista;
    }

    private static BoxScore getBoxScoreFromResultSet(ResultSet boxScoreResultSet) throws SQLException {
        Integer id = boxScoreResultSet.getInt("ID");
        Integer idStudenta = boxScoreResultSet.getInt("ID_STUDENTA");
        Integer kosevi = boxScoreResultSet.getInt("KOSEVI");
        Integer asistencije = boxScoreResultSet.getInt("ASISTENCIJE");
        Integer skokovi = boxScoreResultSet.getInt("SKOKOVI");
        Integer ukradene = boxScoreResultSet.getInt("UKRADENE");
        Integer blokovi = boxScoreResultSet.getInt("BLOKOVI");
        Integer opucaneTrice = boxScoreResultSet.getInt("OPUCANE_TRICE");
        Integer opucaneDvice = boxScoreResultSet.getInt("OPUCANE_DVICE");
        Integer opucanaSlobodna = boxScoreResultSet.getInt("OPUCANA_SLOBODNA");
        Integer zabijeneTrice = boxScoreResultSet.getInt("ZABIJENE_TRICE");
        Integer zabijeneDvice = boxScoreResultSet.getInt("ZABIJENE_DVICE");
        Integer zabijenaSlobodna = boxScoreResultSet.getInt("ZABIJENA_SLOBODNA");
        Integer fauli = boxScoreResultSet.getInt("FAULI");
        Integer izgubljene=boxScoreResultSet.getInt("IZGUBLJENE");

        return new BoxScore(id, idStudenta, kosevi, asistencije, skokovi, ukradene, blokovi, opucaneTrice, opucaneDvice, opucanaSlobodna, zabijeneTrice, zabijeneDvice, zabijenaSlobodna, fauli,izgubljene);
    }

    public static List<BoxScore> dohvatiBoxScorePoIdStudenta(Integer idStudenta) throws SQLException {
        BoxScore boxScoreNovi = new BoxScore(idStudenta);
        List<BoxScore> boxScores=new ArrayList<>();
        try (Connection veza = spojiSeSBazom()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM BOXSCORE WHERE 1 = 1");
            sqlUpit.append(" AND ID_STUDENTA = '" +
                    idStudenta + "'");

            Statement upit = veza.createStatement();
            ResultSet boxScoreResultSet = upit.executeQuery(sqlUpit.toString());
            while (boxScoreResultSet.next()) {
                Integer id = boxScoreResultSet.getInt("ID");
                Integer idStudentaNovi = boxScoreResultSet.getInt("ID_STUDENTA");
                Integer kosevi = boxScoreResultSet.getInt("KOSEVI");
                Integer asistencije = boxScoreResultSet.getInt("ASISTENCIJE");
                Integer skokovi = boxScoreResultSet.getInt("SKOKOVI");
                Integer ukradene = boxScoreResultSet.getInt("UKRADENE");
                Integer blokovi = boxScoreResultSet.getInt("BLOKOVI");
                Integer opucaneTrice = boxScoreResultSet.getInt("OPUCANE_TRICE");
                Integer opucaneDvice = boxScoreResultSet.getInt("OPUCANE_DVICE");
                Integer opucanaSlobodna = boxScoreResultSet.getInt("OPUCANA_SLOBODNA");
                Integer zabijeneTrice = boxScoreResultSet.getInt("ZABIJENE_TRICE");
                Integer zabijeneDvice = boxScoreResultSet.getInt("ZABIJENE_DVICE");
                Integer zabijenaSlobodna = boxScoreResultSet.getInt("ZABIJENA_SLOBODNA");
                Integer fauli = boxScoreResultSet.getInt("FAULI");
                Integer izgubljene=boxScoreResultSet.getInt("IZGUBLJENE");


                boxScoreNovi = new BoxScore(id, idStudenta, kosevi, asistencije, skokovi, ukradene, blokovi, opucaneTrice, opucaneDvice, opucanaSlobodna, zabijeneTrice, zabijeneDvice, zabijenaSlobodna, fauli,izgubljene);
                boxScores.add(boxScoreNovi);
            }
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return boxScores;
    }

    public static BoxScore dohvatiBoxScorePoId(Integer id) throws SQLException {
        BoxScore boxScoreNovi=new BoxScore();
        try (Connection veza = spojiSeSBazom()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM BOXSCORE WHERE 1 = 1");
            sqlUpit.append(" AND ID = '" +
                    id + "'");

            Statement upit = veza.createStatement();
            ResultSet boxScoreResultSet = upit.executeQuery(sqlUpit.toString());
            while (boxScoreResultSet.next()) {
                Integer idnovi = boxScoreResultSet.getInt("ID");
                Integer idStudentaNovi = boxScoreResultSet.getInt("ID_STUDENTA");
                Integer kosevi = boxScoreResultSet.getInt("KOSEVI");
                Integer asistencije = boxScoreResultSet.getInt("ASISTENCIJE");
                Integer skokovi = boxScoreResultSet.getInt("SKOKOVI");
                Integer ukradene = boxScoreResultSet.getInt("UKRADENE");
                Integer blokovi = boxScoreResultSet.getInt("BLOKOVI");
                Integer opucaneTrice = boxScoreResultSet.getInt("OPUCANE_TRICE");
                Integer opucaneDvice = boxScoreResultSet.getInt("OPUCANE_DVICE");
                Integer opucanaSlobodna = boxScoreResultSet.getInt("OPUCANA_SLOBODNA");
                Integer zabijeneTrice = boxScoreResultSet.getInt("ZABIJENE_TRICE");
                Integer zabijeneDvice = boxScoreResultSet.getInt("ZABIJENE_DVICE");
                Integer zabijenaSlobodna = boxScoreResultSet.getInt("ZABIJENA_SLOBODNA");
                Integer fauli = boxScoreResultSet.getInt("FAULI");
                Integer izgubljene=boxScoreResultSet.getInt("IZGUBLJENE");


                boxScoreNovi = new BoxScore(id, idStudentaNovi, kosevi, asistencije, skokovi, ukradene, blokovi, opucaneTrice, opucaneDvice, opucanaSlobodna, zabijeneTrice, zabijeneDvice, zabijenaSlobodna, fauli,izgubljene);
            }
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return boxScoreNovi;
    }
    public static void unesiNoviBoxScore(BoxScore boxScore){
        try (Connection veza = spojiSeSBazom()) {
            PreparedStatement preparedStatement = veza
                    .prepareStatement(
                            "INSERT INTO BOXSCORE(ID_STUDENTA,KOSEVI,ASISTENCIJE,SKOKOVI,UKRADENE,BLOKOVI,OPUCANE_TRICE,OPUCANE_DVICE,OPUCANA_SLOBODNA,ZABIJENE_TRICE,ZABIJENE_DVICE,ZABIJENA_SLOBODNA,FAULI,IZGUBLJENE) VALUES (?,?, ?, ?, ?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, boxScore.getIdStudenta());
            preparedStatement.setInt(2, boxScore.getKosevi());
            preparedStatement.setInt(3, boxScore.getAsistencije());
            preparedStatement.setInt(4, boxScore.getSkokovi());
            preparedStatement.setInt(5, boxScore.getUkradene());
            preparedStatement.setInt(6, boxScore.getBlokovi());
            preparedStatement.setInt(7, boxScore.getOpucaneTrice());
            preparedStatement.setInt(8, boxScore.getOpucaneDvice());
            preparedStatement.setInt(9, boxScore.getOpucanaSlobodna());
            preparedStatement.setInt(10, boxScore.getZabijeneTrice());
            preparedStatement.setInt(11, boxScore.getZabijeneDvice());
            preparedStatement.setInt(12, boxScore.getZabijenaSlobodna());
            preparedStatement.setInt(13, boxScore.getFauli());
            preparedStatement.setInt(14, boxScore.getIzgubljene());

            preparedStatement.executeUpdate();
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
        }
    }



    public static List<Utakmica> dohvatiSveUtakmice() throws SQLException, IOException {

        Connection connection = spojiSeSBazom();

        List<Utakmica> utakmiceLista = new ArrayList<>();

        Statement sqlStatement = connection.createStatement();

        ResultSet utakmiceResultSet = sqlStatement.executeQuery("SELECT * FROM utakmica");

        while (utakmiceResultSet.next()) {
            Utakmica novaUtakmica = getUtakmicaFromResultSet(utakmiceResultSet);
            utakmiceLista.add(novaUtakmica);
        }

        connection.close();

        return utakmiceLista;
    }

    public static Integer dohvatiIdZadnjeUtakmice() throws SQLException {;
        Integer idUtakmica=0;
        try (Connection veza = spojiSeSBazom()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT MAX(ID) FROM UTAKMICA WHERE 1 = 1");

            Statement upit = veza.createStatement();
            ResultSet utakmicaResultSet = upit.executeQuery(sqlUpit.toString());
            while (utakmicaResultSet.next()) {
                idUtakmica = utakmicaResultSet.getInt("MAX(ID)");

            }
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return idUtakmica;
    }

    private static Utakmica getUtakmicaFromResultSet(ResultSet utakmicaResultSet) throws SQLException {
        Integer id = utakmicaResultSet.getInt("ID");
        Integer idFaks1 = utakmicaResultSet.getInt("ID_FAKS1");
        Integer idFaks2 = utakmicaResultSet.getInt("ID_FAKS2");


        return new Utakmica(id, idFaks1,idFaks2);
    }


    public static void unesiNovuUtakmicu(Utakmica utakmica){
        try (Connection veza = spojiSeSBazom()) {
            PreparedStatement preparedStatement = veza
                    .prepareStatement(
                            "INSERT INTO UTAKMICA(ID_FAKS1,ID_FAKS2) VALUES (?, ?)");
            preparedStatement.setInt(1, utakmica.getFaks1Id());
            preparedStatement.setInt(2, utakmica.getFaks2Id());

            preparedStatement.executeUpdate();
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
        }
    }

    public static List<Performans> dohvatiSvePerformanse() throws SQLException, IOException {

        Connection connection = spojiSeSBazom();

        List<Performans> performanseLista = new ArrayList<>();

        Statement sqlStatement = connection.createStatement();

        ResultSet performansResultSet = sqlStatement.executeQuery("SELECT * FROM performans");

        while (performansResultSet.next()) {
            Performans noviPerformans= getPerformansFromResultSet(performansResultSet);
            performanseLista.add(noviPerformans);
        }

        connection.close();

        return performanseLista;
    }

    private static Performans getPerformansFromResultSet(ResultSet performansResultSet) throws SQLException {
        Integer id = performansResultSet.getInt("ID");
        Integer idUtakmica = performansResultSet.getInt("ID_UTAKMICA");
        Integer idStudent = performansResultSet.getInt("ID_STUDENT");
        Integer idBoxScore = performansResultSet.getInt("ID_BOXSCORE");
        Integer bodovi = performansResultSet.getInt("BODOVI");

        return new Performans(id, idUtakmica,idStudent,idBoxScore,bodovi);
    }


    public static List<Performans> dohvatiPerformansePoIdUtakmice(Integer idUtakmice) throws SQLException {
        List<Performans> performanse=new ArrayList<>();
        try (Connection veza = spojiSeSBazom()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM PERFORMANS WHERE 1 = 1");
            sqlUpit.append(" AND ID_UTAKMICA = '" +
                    idUtakmice + "'");

            Statement upit = veza.createStatement();
            ResultSet performansResultSet = upit.executeQuery(sqlUpit.toString());
            while (performansResultSet.next()) {
                Integer id = performansResultSet.getInt("ID");
                Integer idUtakmica = performansResultSet.getInt("ID_UTAKMICA");
                Integer idStudent = performansResultSet.getInt("ID_STUDENT");
                Integer idBoxScore = performansResultSet.getInt("ID_BOXSCORE");
                Integer bodovi = performansResultSet.getInt("BODOVI");

                Performans performans= new Performans(id, idUtakmica,idStudent,idBoxScore,bodovi);
                performanse.add(performans);
            }
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return performanse;
    }

    public static Performans dohvatiPerformansePoIdUtakmiceIdStudenta(Integer idUtakmice,Integer idStudent) throws SQLException {
        Performans performans=new Performans();
        try (Connection veza = spojiSeSBazom()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM PERFORMANS WHERE 1 = 1");
            sqlUpit.append(" AND ID_UTAKMICA = '" +
                    idUtakmice + "'");
            sqlUpit.append(" AND ID_STUDENT = '" +
                    idStudent + "'");

            Statement upit = veza.createStatement();
            ResultSet performansResultSet = upit.executeQuery(sqlUpit.toString());
            while (performansResultSet.next()) {
                Integer id = performansResultSet.getInt("ID");
                Integer idUtakmica = performansResultSet.getInt("ID_UTAKMICA");
                Integer idStudentnovi = performansResultSet.getInt("ID_STUDENT");
                Integer idBoxScore = performansResultSet.getInt("ID_BOXSCORE");
                Integer bodovi = performansResultSet.getInt("BODOVI");

                performans = new Performans(id, idUtakmica,idStudent,idBoxScore,bodovi);

            }
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return performans;
    }


    public static void unesiNoviPerformans(Performans performans){
        try (Connection veza = spojiSeSBazom()) {
            PreparedStatement preparedStatement = veza
                    .prepareStatement(
                            "INSERT INTO PERFORMANS(ID_UTAKMICA,ID_STUDENT,ID_BOXSCORE,BODOVI) VALUES (?, ?,?,?)");
            preparedStatement.setInt(1, performans.getIdUtakmica());
            preparedStatement.setInt(2, performans.getIdStudent());
            preparedStatement.setInt(3, performans.getIdBoxScore());
            preparedStatement.setInt(4, performans.getBodovi());

            preparedStatement.executeUpdate();
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
        }
    }

    public static Integer dohvatiZadnjiBoxScoreId() throws SQLException {;
        Integer idBoxScore=0;
        try (Connection veza = spojiSeSBazom()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT MAX(ID) FROM BOXSCORE WHERE 1 = 1");

            Statement upit = veza.createStatement();
            ResultSet boxSCoreResultSet = upit.executeQuery(sqlUpit.toString());
            while (boxSCoreResultSet.next()) {
                idBoxScore = boxSCoreResultSet.getInt("MAX(ID)");

            }
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return idBoxScore;
    }

    public static List<Liga> dohvatiSveLige() throws SQLException, IOException {

        Connection connection = spojiSeSBazom();

        List<Liga> ligeLista = new ArrayList<>();

        Statement sqlStatement = connection.createStatement();

        ResultSet ligaResultSet = sqlStatement.executeQuery("SELECT * FROM liga");

        while (ligaResultSet.next()) {
            Liga novaLiga= getLigaFromResultSet(ligaResultSet);
            ligeLista.add(novaLiga);
        }

        connection.close();

        return ligeLista;
    }



    private static Liga getLigaFromResultSet(ResultSet ligaResultSet) throws SQLException {
        Integer id = ligaResultSet.getInt("ID");
        String ime=ligaResultSet.getString("IME");
        String sifra=ligaResultSet.getString("SIFRA");

        return new Liga(id,ime,sifra);
    }


    public static void unesiNovuLigu(Liga liga){
        try (Connection veza = spojiSeSBazom()) {
            PreparedStatement preparedStatement = veza
                    .prepareStatement(
                            "INSERT INTO LIGA(IME,SIFRA) VALUES (?,?)");
            preparedStatement.setString(1, liga.getIme());
            preparedStatement.setString(2, liga.getSifra());

            preparedStatement.executeUpdate();
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
        }
    }

    public static List<KorisnikLiga> dohvatiKorisnikeLige(Integer ligaId) throws SQLException {
        List<KorisnikLiga> listaKorisnikaLige = new ArrayList<>();
        try (Connection veza = spojiSeSBazom()) {

            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM KORISNIK_LIGA WHERE 1 = 1");
            if (ligaId != 0) {
                sqlUpit.append("AND ID_LIGA =  " + ligaId);
            }
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Integer idNew = resultSet.getInt("id");
                Integer idKorisnikNew = resultSet.getInt("id_korisnik");
                Integer idLigaNew = resultSet.getInt("id_liga");

                KorisnikLiga noviKorisnikLige = new KorisnikLiga(idNew, idLigaNew, idKorisnikNew);
                listaKorisnikaLige.add(noviKorisnikLige);
            }
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return listaKorisnikaLige;
    }

    public static void unesiNovogKorisnikaLige(KorisnikLiga korisnikLiga){
        try (Connection veza = spojiSeSBazom()) {
            PreparedStatement preparedStatement = veza
                    .prepareStatement(
                            "INSERT INTO KORISNIK_LIGA(ID_LIGA,ID_KORISNIK) VALUES (?,?)");
            preparedStatement.setInt(1, korisnikLiga.getIdLiga());
            preparedStatement.setInt(2, korisnikLiga.getIdKorisnik());

            preparedStatement.executeUpdate();
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
        }
    }

    public static Optional<BodoviLiga> dohvatiBodoveKorisnikaLige(Integer korisnikId,Integer ligaId) throws SQLException {
        Optional<BodoviLiga> bodoviLiga= Optional.empty();
        try (Connection veza = spojiSeSBazom()) {

            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM BODOVI_LIGA WHERE 1 = 1");
            if (ligaId != 0) {
                sqlUpit.append("AND ID_LIGA =  " + ligaId);
                sqlUpit.append("AND ID_KORISNIK = "+korisnikId);
            }
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Integer idNew = resultSet.getInt("id");
                Integer idKorisnikNew = resultSet.getInt("id_korisnik");
                Integer idLigaNew = resultSet.getInt("id_liga");
                Double brojBodova= resultSet.getDouble("broj_bodova");

                 bodoviLiga = Optional.of(new BodoviLiga(idNew, idKorisnikNew, idLigaNew, brojBodova));
                System.out.println("BODOVI U BAZI"+ bodoviLiga);
            }
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return bodoviLiga;
    }

    public static void unesiNoveBodoveLige(BodoviLiga bodoviLiga){
        try (Connection veza = spojiSeSBazom()) {
            PreparedStatement preparedStatement = veza
                    .prepareStatement(
                            "INSERT INTO BODOVI_LIGA(ID_LIGA,ID_KORISNIK,BROJ_BODOVA) VALUES (?,?,?)");
            preparedStatement.setInt(1, bodoviLiga.getIdLiga());
            preparedStatement.setInt(2, bodoviLiga.getIdKorisnik());
            preparedStatement.setDouble(3, bodoviLiga.getBrojBodova());

            preparedStatement.executeUpdate();
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
        }
    }

    public static BodoviLiga promjeniBodove(BodoviLiga bodoviLiga,Integer bodoviZaDodati) throws SQLException, IOException {
        Connection connection = spojiSeSBazom();
        Double ukupno=bodoviZaDodati+bodoviLiga.getBrojBodova();
            PreparedStatement updateBodovi =
                    connection.prepareStatement(
                            "UPDATE BODOVI_LIGA SET BROJ_BODOVA = ? WHERE (ID_KORISNIK = ? AND ID_LIGA = ?)");
            updateBodovi.setDouble(1, ukupno);
            updateBodovi.setInt(2,bodoviLiga.getIdKorisnik());
            updateBodovi.setInt(3,bodoviLiga.getIdLiga());
            updateBodovi.executeUpdate();

        connection.close();
        return bodoviLiga;
    }


    public static List<Rezultat> dohvatiSveRezultate() throws SQLException, IOException {

        Connection connection = spojiSeSBazom();

        List<Rezultat> rezultatiLista = new ArrayList<>();

        Statement sqlStatement = connection.createStatement();

        ResultSet rezultatResultSet = sqlStatement.executeQuery("SELECT * FROM rezultat");

        while (rezultatResultSet.next()) {
            Rezultat noviRezultat = getRezultatFromResultSet(rezultatResultSet);
            rezultatiLista.add(noviRezultat);
        }

        connection.close();

        return rezultatiLista;
    }

    private static Rezultat getRezultatFromResultSet(ResultSet rezultatResultSet) throws SQLException {
        Integer id = rezultatResultSet.getInt("ID");
        Integer idUtakmica=rezultatResultSet.getInt("ID_UTAKMICA");
        Integer brojKosevaDomaci=rezultatResultSet.getInt("BROJ_KOSEVA_DOMACI");
        Integer brojKosevaGosti=rezultatResultSet.getInt("BROJ_KOSEVA_GOSTI");

        return new Rezultat(id,idUtakmica,brojKosevaDomaci,brojKosevaGosti);
    }

    public static Rezultat dohvatiRezultatPoId(Integer id) throws SQLException {
        Rezultat rezultat=new Rezultat();
        try (Connection veza = spojiSeSBazom()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM REZULTAT WHERE 1 = 1");
            sqlUpit.append(" AND ID = '" +
                    id + "'");

            Statement upit = veza.createStatement();
            ResultSet rezultatResultSet = upit.executeQuery(sqlUpit.toString());
            while (rezultatResultSet.next()) {
                Integer idNovi = rezultatResultSet.getInt("ID");
                Integer idUtakmica= rezultatResultSet.getInt("ID_UTAKMICA");
                Integer brojKosevaDomaci=rezultatResultSet.getInt("BROJ_KOSEVA_DOMACI");
                Integer brojKosevaGosti=rezultatResultSet.getInt("BROJ_KOSEVA_GOSTI");

                rezultat = new Rezultat(id, idUtakmica,brojKosevaDomaci,brojKosevaGosti);
            }
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return rezultat;
    }

    public static Rezultat dohvatiRezultatPoIdUtakmice(Integer idUtakmice) throws SQLException {
        Rezultat rezultat=new Rezultat();
        try (Connection veza = spojiSeSBazom()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM REZULTAT WHERE 1 = 1");
            sqlUpit.append(" AND ID_UTAKMICA = '" +
                    idUtakmice + "'");

            Statement upit = veza.createStatement();
            ResultSet rezultatResultSet = upit.executeQuery(sqlUpit.toString());
            while (rezultatResultSet.next()) {
                Integer idNovi = rezultatResultSet.getInt("ID");
                Integer idUtakmica= rezultatResultSet.getInt("ID_UTAKMICA");
                Integer brojKosevaDomaci=rezultatResultSet.getInt("BROJ_KOSEVA_DOMACI");
                Integer brojKosevaGosti=rezultatResultSet.getInt("BROJ_KOSEVA_GOSTI");

                rezultat = new Rezultat(idNovi, idUtakmica,brojKosevaDomaci,brojKosevaGosti);
            }
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return rezultat;
    }
    public static void unesiNoviRezultat(Rezultat rezultat){
        try (Connection veza = spojiSeSBazom()) {
            PreparedStatement preparedStatement = veza
                    .prepareStatement(
                            "INSERT INTO REZULTAT(ID_UTAKMICA,BROJ_KOSEVA_DOMACI,BROJ_KOSEVA_GOSTI) VALUES (?,?, ?)");
            preparedStatement.setInt(1, rezultat.getIdUtakmica());
            preparedStatement.setInt(2, rezultat.getBrojKosevaDomaci());
            preparedStatement.setInt(3, rezultat.getBrojKosevaGosti());

            preparedStatement.executeUpdate();
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
        }
    }

    public static Kljuc dohvatiKljuc() throws SQLException {
        Kljuc kljuc=new Kljuc();
        try (Connection veza = spojiSeSBazom()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM KLJUC");
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Integer zauzet = resultSet.getInt("ZAUZET");
                String naziv = resultSet.getString("NAZIV");

                 kljuc=new Kljuc(naziv,zauzet);
            }
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new SQLException(poruka, ex);
        }
        return kljuc;
    }

    public static void promjeniKljuc(Kljuc kljuc){
        try (Connection veza = spojiSeSBazom()) {
            PreparedStatement preparedStatement = veza
                    .prepareStatement(
                            "UPDATE KLJUC SET ZAUZET = ? WHERE NAZIV = ?");
            preparedStatement.setInt(1, kljuc.getZauzet());
            preparedStatement.setString(2, kljuc.getNaziv());

            preparedStatement.executeUpdate();
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
        }
    }
}
