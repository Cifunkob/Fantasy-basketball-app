package entiteti;

import java.util.Objects;

public class Performans {
    private Integer id;
    private Integer idUtakmica;
    private Integer idStudent;

    private Integer idBoxScore;
    private Integer bodovi;

    public Performans(Integer id, Integer idUtakmica, Integer idStudent, Integer idBoxScore, Integer bodovi) {
        this.id = id;
        this.idUtakmica = idUtakmica;
        this.idStudent = idStudent;
        this.idBoxScore = idBoxScore;
        this.bodovi = bodovi;
    }

    public Performans(Integer idUtakmica, Integer idStudent, Integer idBoxScore, Integer bodovi) {
        this.idUtakmica = idUtakmica;
        this.idStudent = idStudent;
        this.idBoxScore = idBoxScore;
        this.bodovi = bodovi;
    }

    public Performans() {
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

    public Integer getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Integer idStudent) {
        this.idStudent = idStudent;
    }

    public Integer getIdBoxScore() {
        return idBoxScore;
    }

    public void setIdBoxScore(Integer idBoxScore) {
        this.idBoxScore = idBoxScore;
    }

    public Integer getBodovi() {
        return bodovi;
    }

    public void setBodovi(Integer bodovi) {
        this.bodovi = bodovi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Performans that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(idUtakmica, that.idUtakmica) && Objects.equals(idStudent, that.idStudent) && Objects.equals(idBoxScore, that.idBoxScore) && Objects.equals(bodovi, that.bodovi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUtakmica, idStudent, idBoxScore, bodovi);
    }

    @Override
    public String toString() {
        return "Performans{" +
                "id=" + id +
                ", idUtakmica=" + idUtakmica +
                ", idStudent=" + idStudent +
                ", idBoxScore=" + idBoxScore +
                ", bodovi=" + bodovi +
                '}';
    }
}
