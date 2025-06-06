package entiteti;

public class Fakultet {
    private Integer id;
    private String naziv;

    public Fakultet(Integer id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }

    public Fakultet() {
    }

    @Override
    public String toString() {
        return "Fakultet{" +
                "id=" + id +
                ", naziv='" + naziv + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
