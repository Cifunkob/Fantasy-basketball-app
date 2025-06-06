package entiteti;

public class Kljuc {
    private String naziv;
    private Integer zauzet;

    public Kljuc(String naziv, Integer zauzet) {
        this.naziv = naziv;
        this.zauzet = zauzet;
    }

    public Kljuc() {
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getZauzet() {
        return zauzet;
    }

    public void setZauzet(Integer zauzet) {
        this.zauzet = zauzet;
    }
}
