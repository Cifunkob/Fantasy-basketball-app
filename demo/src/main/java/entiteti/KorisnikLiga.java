package entiteti;

import java.util.Objects;

public class KorisnikLiga {
    Integer id;
    Integer idLiga;
    Integer idKorisnik;

    public KorisnikLiga(Integer id, Integer idLiga, Integer idKorisnik) {
        this.id = id;
        this.idLiga = idLiga;
        this.idKorisnik = idKorisnik;
    }

    public KorisnikLiga(Integer idLiga, Integer idKorisnik) {
        this.idLiga = idLiga;
        this.idKorisnik = idKorisnik;
    }

    public KorisnikLiga() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdLiga() {
        return idLiga;
    }

    public void setIdLiga(Integer idLiga) {
        this.idLiga = idLiga;
    }

    public Integer getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(Integer idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KorisnikLiga that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(idLiga, that.idLiga) && Objects.equals(idKorisnik, that.idKorisnik);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idLiga, idKorisnik);
    }

    @Override
    public String toString() {
        return "KorisnikLiga{" +
                "id=" + id +
                ", idLiga=" + idLiga +
                ", idKorisnik=" + idKorisnik +
                '}';
    }
}
