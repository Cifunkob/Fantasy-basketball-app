package entiteti;

import java.util.Objects;

public class Rezultat {
    private Integer id;
    private Integer idUtakmica;
    private Integer brojKosevaDomaci;
    private Integer brojKosevaGosti;
    public Rezultat() {
    }
    public Rezultat(Integer idUtakmica, Integer brojKosevaDomaci, Integer brojKosevaGosti) {
        this.idUtakmica = idUtakmica;
        this.brojKosevaDomaci = brojKosevaDomaci;
        this.brojKosevaGosti = brojKosevaGosti;
    }

    public Rezultat(Integer id, Integer idUtakmica, Integer brojKosevaDomaci, Integer brojKosevaGosti) {
        this.id = id;
        this.idUtakmica = idUtakmica;
        this.brojKosevaDomaci = brojKosevaDomaci;
        this.brojKosevaGosti = brojKosevaGosti;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUtakmica() {
        return idUtakmica;
    }

    public void setIdUtakmica(Integer idUtakmica) {
        this.idUtakmica = idUtakmica;
    }

    public Integer getBrojKosevaDomaci() {
        return brojKosevaDomaci;
    }

    public void setBrojKosevaDomaci(Integer brojKosevaDomaci) {
        this.brojKosevaDomaci = brojKosevaDomaci;
    }

    public Integer getBrojKosevaGosti() {
        return brojKosevaGosti;
    }

    public void setBrojKosevaGosti(Integer brojKosevaGosti) {
        this.brojKosevaGosti = brojKosevaGosti;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rezultat rezultat)) return false;
        return Objects.equals(id, rezultat.id) && Objects.equals(idUtakmica, rezultat.idUtakmica) && Objects.equals(brojKosevaDomaci, rezultat.brojKosevaDomaci) && Objects.equals(brojKosevaGosti, rezultat.brojKosevaGosti);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUtakmica, brojKosevaDomaci, brojKosevaGosti);
    }

    @Override
    public String toString() {
        return "Rezultat{" +
                "id=" + id +
                ", idUtakmica=" + idUtakmica +
                ", brojKosevaDomaci=" + brojKosevaDomaci +
                ", brojKosevaGosti=" + brojKosevaGosti +
                '}';
    }
}
