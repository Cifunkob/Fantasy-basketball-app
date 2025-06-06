package entiteti;

import java.util.Objects;

public class Liga {
    private Integer id;
    private String ime;
    private String sifra;

    public Liga(Integer id, String ime, String sifra) {
        this.id = id;
        this.ime = ime;
        this.sifra = sifra;
    }

    public Liga() {
    }

    public Liga(String ime, String sifra) {
        this.ime = ime;
        this.sifra = sifra;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    @Override
    public String toString() {
        return "Liga{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                ", sifra='" + sifra + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Liga liga)) return false;
        return Objects.equals(id, liga.id) && Objects.equals(ime, liga.ime) && Objects.equals(sifra, liga.sifra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ime, sifra);
    }
}
