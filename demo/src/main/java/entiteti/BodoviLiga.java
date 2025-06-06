package entiteti;

import java.util.Objects;

public class BodoviLiga {
    private Integer id;
    private Integer idKorisnik;
    private Integer idLiga;
    private Double brojBodova;

    public BodoviLiga(Integer id, Integer idKorisnik, Integer idLiga, Double brojBodova) {
        this.id = id;
        this.idKorisnik = idKorisnik;
        this.idLiga = idLiga;
        this.brojBodova = brojBodova;
    }

    public BodoviLiga(Integer idKorisnik, Integer idLiga, Double brojBodova) {
        this.idKorisnik = idKorisnik;
        this.idLiga = idLiga;
        this.brojBodova = brojBodova;
    }

    public BodoviLiga() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(Integer idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    public Integer getIdLiga() {
        return idLiga;
    }

    public void setIdLiga(Integer idLiga) {
        this.idLiga = idLiga;
    }

    public Double getBrojBodova() {
        return brojBodova;
    }

    public void setBrojBodova(Double brojBodova) {
        this.brojBodova = brojBodova;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BodoviLiga that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(idKorisnik, that.idKorisnik) && Objects.equals(idLiga, that.idLiga) && Objects.equals(brojBodova, that.brojBodova);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idKorisnik, idLiga, brojBodova);
    }

    @Override
    public String toString() {
        return "BodoviLiga{" +
                "id=" + id +
                ", idKorisnik=" + idKorisnik +
                ", idLiga=" + idLiga +
                ", brojBodova=" + brojBodova +
                '}';
    }
}
