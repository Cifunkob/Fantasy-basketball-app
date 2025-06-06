package entiteti;

import java.time.LocalDate;
import java.util.Objects;

public class Stats {
    private Integer id;
    private Integer idStudent;
    private Integer sutZaDva;
    private Integer sutZaTri;
    private Integer obranaPerim;
    private Integer obranaInt;
    private Integer skok;
    private Integer visina;
    private Integer kilaza;
    private Integer dribling;
    private Integer ukradene;
    private Integer blok;

    public Stats(Integer id, Integer idStudent, Integer sutZaDva, Integer sutZaTri, Integer obranaPerim, Integer obranaInt, Integer skok, Integer visina, Integer kilaza, Integer dribling, Integer ukradene, Integer blok) {
        this.id = id;
        this.idStudent = idStudent;
        this.sutZaDva = sutZaDva;
        this.sutZaTri = sutZaTri;
        this.obranaPerim = obranaPerim;
        this.obranaInt = obranaInt;
        this.skok = skok;
        this.visina = visina;
        this.kilaza = kilaza;
        this.dribling = dribling;
        this.ukradene = ukradene;
        this.blok = blok;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Integer idStudent) {
        this.idStudent = idStudent;
    }

    public Integer getSutZaDva() {
        return sutZaDva;
    }

    public void setSutZaDva(Integer sutZaDva) {
        this.sutZaDva = sutZaDva;
    }

    public Integer getSutZaTri() {
        return sutZaTri;
    }

    public void setSutZaTri(Integer sutZaTri) {
        this.sutZaTri = sutZaTri;
    }

    public Integer getObranaPerim() {
        return obranaPerim;
    }

    public void setObranaPerim(Integer obranaPerim) {
        this.obranaPerim = obranaPerim;
    }

    public Integer getObranaInt() {
        return obranaInt;
    }

    public void setObranaInt(Integer obranaInt) {
        this.obranaInt = obranaInt;
    }

    public Integer getSkok() {
        return skok;
    }

    public void setSkok(Integer skok) {
        this.skok = skok;
    }

    public Integer getVisina() {
        return visina;
    }

    public void setVisina(Integer visina) {
        this.visina = visina;
    }

    public Integer getKilaza() {
        return kilaza;
    }

    public void setKilaza(Integer kilaza) {
        this.kilaza = kilaza;
    }

    public Integer getDribling() {
        return dribling;
    }

    public void setDribling(Integer dribling) {
        this.dribling = dribling;
    }

    public Integer getUkradene() {
        return ukradene;
    }

    public void setUkradene(Integer ukradene) {
        this.ukradene = ukradene;
    }

    public Integer getBlok() {
        return blok;
    }

    public void setBlok(Integer blok) {
        this.blok = blok;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stats stats)) return false;
        return Objects.equals(id, stats.id) && Objects.equals(idStudent, stats.idStudent) && Objects.equals(sutZaDva, stats.sutZaDva) && Objects.equals(sutZaTri, stats.sutZaTri) && Objects.equals(obranaPerim, stats.obranaPerim) && Objects.equals(obranaInt, stats.obranaInt) && Objects.equals(skok, stats.skok) && Objects.equals(visina, stats.visina) && Objects.equals(kilaza, stats.kilaza) && Objects.equals(dribling, stats.dribling) && Objects.equals(ukradene, stats.ukradene) && Objects.equals(blok, stats.blok);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idStudent, sutZaDva, sutZaTri, obranaPerim, obranaInt, skok, visina, kilaza, dribling, ukradene, blok);
    }

    public Stats() {
    }

    @Override
    public String toString() {
        return "Stats{" +
                "id=" + id +
                ", idStudent=" + idStudent +
                ", sutZaDva=" + sutZaDva +
                ", sutZaTri=" + sutZaTri +
                ", obranaPerim=" + obranaPerim +
                ", obranaInt=" + obranaInt +
                ", skok=" + skok +
                ", visina=" + visina +
                ", kilaza=" + kilaza +
                ", dribling=" + dribling +
                ", ukradene=" + ukradene +
                ", blok=" + blok +
                '}';
    }
}
