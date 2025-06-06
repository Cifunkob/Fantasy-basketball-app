package entiteti;

import java.util.Objects;

public class BoxScore {
    private Integer id;
    private Integer idStudenta;
    private Integer kosevi;
    private Integer asistencije;
    private Integer skokovi;
    private Integer ukradene;
    private Integer blokovi;
    private Integer opucaneTrice;
    private Integer opucaneDvice;
    private Integer opucanaSlobodna;
    private Integer zabijeneTrice;
    private Integer zabijeneDvice;
    private Integer zabijenaSlobodna;
    private Integer fauli;
    private Integer izgubljene;

    public BoxScore(Integer id, Integer idStudenta, Integer kosevi, Integer asistencije, Integer skokovi, Integer ukradene, Integer blokovi, Integer opucaneTrice, Integer opucaneDvice, Integer opucanaSlobodna, Integer zabijeneTrice, Integer zabijeneDvice, Integer zabijenaSlobodna, Integer fauli, Integer izgubljene) {
        this.id = id;
        this.idStudenta = idStudenta;
        this.kosevi = kosevi;
        this.asistencije = asistencije;
        this.skokovi = skokovi;
        this.ukradene = ukradene;
        this.blokovi = blokovi;
        this.opucaneTrice = opucaneTrice;
        this.opucaneDvice = opucaneDvice;
        this.opucanaSlobodna = opucanaSlobodna;
        this.zabijeneTrice = zabijeneTrice;
        this.zabijeneDvice = zabijeneDvice;
        this.zabijenaSlobodna = zabijenaSlobodna;
        this.fauli = fauli;
        this.izgubljene = izgubljene;
    }

    public BoxScore(Integer idStudenta) {
        this.idStudenta = idStudenta;
        this.kosevi = 0;
        this.asistencije = 0;
        this.skokovi = 0;
        this.ukradene = 0;
        this.blokovi = 0;
        this.opucaneTrice = 0;
        this.opucaneDvice = 0;
        this.opucanaSlobodna = 0;
        this.zabijeneTrice = 0;
        this.zabijeneDvice = 0;
        this.zabijenaSlobodna = 0;
        this.fauli = 0;
        this.izgubljene = 0;
    }

    public BoxScore() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdStudenta() {
        return idStudenta;
    }

    public void setIdStudenta(Integer idStudenta) {
        this.idStudenta = idStudenta;
    }

    public Integer getKosevi() {
        return kosevi;
    }

    public void setKosevi(Integer kosevi) {
        this.kosevi = kosevi;
    }

    public Integer getAsistencije() {
        return asistencije;
    }

    public void setAsistencije(Integer asistencije) {
        this.asistencije = asistencije;
    }

    public Integer getSkokovi() {
        return skokovi;
    }

    public void setSkokovi(Integer skokovi) {
        this.skokovi = skokovi;
    }

    public Integer getUkradene() {
        return ukradene;
    }

    public void setUkradene(Integer ukradene) {
        this.ukradene = ukradene;
    }

    public Integer getBlokovi() {
        return blokovi;
    }

    public void setBlokovi(Integer blokovi) {
        this.blokovi = blokovi;
    }

    public Integer getOpucaneTrice() {
        return opucaneTrice;
    }

    public void setOpucaneTrice(Integer opucaneTrice) {
        this.opucaneTrice = opucaneTrice;
    }

    public Integer getOpucaneDvice() {
        return opucaneDvice;
    }

    public void setOpucaneDvice(Integer opucaneDvice) {
        this.opucaneDvice = opucaneDvice;
    }

    public Integer getOpucanaSlobodna() {
        return opucanaSlobodna;
    }

    public void setOpucanaSlobodna(Integer opucanaSlobodna) {
        this.opucanaSlobodna = opucanaSlobodna;
    }

    public Integer getZabijeneTrice() {
        return zabijeneTrice;
    }

    public void setZabijeneTrice(Integer zabijeneTrice) {
        this.zabijeneTrice = zabijeneTrice;
    }

    public Integer getZabijeneDvice() {
        return zabijeneDvice;
    }

    public void setZabijeneDvice(Integer zabijeneDvice) {
        this.zabijeneDvice = zabijeneDvice;
    }

    public Integer getZabijenaSlobodna() {
        return zabijenaSlobodna;
    }

    public void setZabijenaSlobodna(Integer zabijenaSlobodna) {
        this.zabijenaSlobodna = zabijenaSlobodna;
    }

    public Integer getFauli() {
        return fauli;
    }

    public void setFauli(Integer fauli) {
        this.fauli = fauli;
    }

    public Integer getIzgubljene() {
        return izgubljene;
    }

    public void setIzgubljene(Integer izgubljene) {
        this.izgubljene = izgubljene;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoxScore boxScore)) return false;
        return Objects.equals(id, boxScore.id) && Objects.equals(idStudenta, boxScore.idStudenta) && Objects.equals(kosevi, boxScore.kosevi) && Objects.equals(asistencije, boxScore.asistencije) && Objects.equals(skokovi, boxScore.skokovi) && Objects.equals(ukradene, boxScore.ukradene) && Objects.equals(blokovi, boxScore.blokovi) && Objects.equals(opucaneTrice, boxScore.opucaneTrice) && Objects.equals(opucaneDvice, boxScore.opucaneDvice) && Objects.equals(opucanaSlobodna, boxScore.opucanaSlobodna) && Objects.equals(zabijeneTrice, boxScore.zabijeneTrice) && Objects.equals(zabijeneDvice, boxScore.zabijeneDvice) && Objects.equals(zabijenaSlobodna, boxScore.zabijenaSlobodna) && Objects.equals(fauli, boxScore.fauli) && Objects.equals(izgubljene, boxScore.izgubljene);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idStudenta, kosevi, asistencije, skokovi, ukradene, blokovi, opucaneTrice, opucaneDvice, opucanaSlobodna, zabijeneTrice, zabijeneDvice, zabijenaSlobodna, fauli, izgubljene);
    }

    @Override
    public String toString() {
        return "BoxScore{" +
                "id=" + id +
                ", idStudenta=" + idStudenta +
                ", kosevi=" + kosevi +
                ", asistencije=" + asistencije +
                ", skokovi=" + skokovi +
                ", ukradene=" + ukradene +
                ", blokovi=" + blokovi +
                ", opucaneTrice=" + opucaneTrice +
                ", opucaneDvice=" + opucaneDvice +
                ", opucanaSlobodna=" + opucanaSlobodna +
                ", zabijeneTrice=" + zabijeneTrice +
                ", zabijeneDvice=" + zabijeneDvice +
                ", zabijenaSlobodna=" + zabijenaSlobodna +
                ", fauli=" + fauli +
                ", izgubljene=" + izgubljene +
                '}';
    }
}
