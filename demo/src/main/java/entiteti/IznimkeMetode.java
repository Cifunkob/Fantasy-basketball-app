package entiteti;

import baza.BazaPodataka;
import hr.tvz.pejkunovic.demo.LigaOdabirController;
import hr.tvz.pejkunovic.demo.LoginScreenController;
import iznimke.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public class IznimkeMetode {
    public static void provjeraDuplogKorisnickogImena(String korisnickoIme) throws DupliKorisnikIznimka {
        List<Korisnik> korisnici;
        try {
            korisnici = BazaPodataka.dohvatiSveKorisnike();
            if (korisnici.stream().filter(v -> v.getUsername().equals(korisnickoIme)).findFirst().isEmpty() == false) {
                throw new DupliKorisnikIznimka();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void provjeraDuzineLozinke(String lozinka) throws KratkaLozinkaIznimka {
        if (lozinka.length() < 6) {
            throw new KratkaLozinkaIznimka();
        }
    }

    public static void provjeraSigurnostiLozinke(String lozinka) throws NesigurnaLozinkaIznimka {
        boolean hasAlphabet = false;
        boolean hasNumber = false;

        for (char c : lozinka.toCharArray()) {
            if (Character.isLetter(c)) {
                hasAlphabet = true;
            }
            if (Character.isDigit(c)) {
                hasNumber = true;
            }
        }
        if (!hasNumber || !hasAlphabet) {
            throw new NesigurnaLozinkaIznimka();
        }
    }

    public static void provjeraKorisnikaPriPrijavi(String korisnickoIme) throws NepostojeciKorisnikIznimka {
        List<Korisnik> korisnici;
        try {
            korisnici = BazaPodataka.dohvatiSveKorisnike();
            if (korisnici.stream().filter(v -> v.getUsername().equals(korisnickoIme)).findFirst().isEmpty() == true) {
                throw new NepostojeciKorisnikIznimka();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void provjeraLozinkePriPrijavi(String lozinka, String korisnickoIme) throws PogresnaLozinkaIznimka {
        Korisnik korisnik=new Korisnik();
        String enkriptiranaLozinka= null;
        try {
            enkriptiranaLozinka = Enkripcija.enkriptiraj(lozinka);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            korisnik=BazaPodataka.dohvatiKorisnikaPremaUsernameu(korisnickoIme);
            if (!korisnik.getPassword().equals(enkriptiranaLozinka)) {
                throw new PogresnaLozinkaIznimka();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void provjeraJeLiObjektOdabran(Object object){
        if(object==null){
            throw new NijeOdabranEntitetIznimka();
        }}

    public static void provjeraImaLiIgracOdigranuUtakmicu(Integer id){
        try {
            List boxScore = BazaPodataka.dohvatiBoxScorePoIdStudenta(id);
            if(boxScore.size()==0){
                throw new IgracNemaOdigranuUtakmicuIznimka();
            } }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void provjeraImaLiKorisnikManjeOd5Igraca() throws PreviseIgracaIznimka{
        try {
            Integer brojIgracaKorisnika = BazaPodataka.dohvatiIgraceKorisnika(LoginScreenController.idKorisnik, LigaOdabirController.liga.getId()).size();
            if(brojIgracaKorisnika==5){
                throw new PreviseIgracaIznimka();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}



