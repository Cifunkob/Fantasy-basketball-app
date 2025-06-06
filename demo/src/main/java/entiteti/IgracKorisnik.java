package entiteti;

public class IgracKorisnik {
    public Integer id;
    public Integer korisnikId;
    public Integer igracId;

    public Integer ligaId;

    public IgracKorisnik(Integer id, Integer korisnikId, Integer igracId, Integer ligaId) {
        this.id = id;
        this.korisnikId = korisnikId;
        this.igracId = igracId;
        this.ligaId = ligaId;
    }

    public IgracKorisnik() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Integer korisnikId) {
        this.korisnikId = korisnikId;
    }

    public Integer getIgracId() {
        return igracId;
    }

    public void setIgracId(Integer igracId) {
        this.igracId = igracId;
    }

    public Integer getLigaId() {
        return ligaId;
    }

    public void setLigaId(Integer ligaId) {
        this.ligaId = ligaId;
    }

    @Override
    public String toString() {
        return "IgracKorisnik{" +
                "id=" + id +
                ", korisnikId=" + korisnikId +
                ", igracId=" + igracId +
                ", ligaId=" + ligaId +
                '}';
    }
}
