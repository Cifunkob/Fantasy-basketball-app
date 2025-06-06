package entiteti;


import java.time.LocalDate;
import java.util.Objects;

public class Student {
private Integer id;
private String ime;
private String prezime;
private Integer fakultetId;
private String pozicija;
private LocalDate datumRodenja;

    public Student(Integer id, String ime, String prezime, Integer fakultet_id, String pozicija, LocalDate datumRodenja) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.fakultetId = fakultet_id;
        this.pozicija = pozicija;
        this.datumRodenja = datumRodenja;
    }

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", fakultetId=" + fakultetId +
                ", pozicija='" + pozicija + '\'' +
                ", datumRodenja=" + datumRodenja +
                '}';
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

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Integer getFakultetId() {
        return fakultetId;
    }

    public void setFakultetId(Integer fakultetId) {
        this.fakultetId = fakultetId;
    }

    public String getPozicija() {
        return pozicija;
    }

    public void setPozicija(String pozicija) {
        this.pozicija = pozicija;
    }

    public LocalDate getDatumRodenja() {
        return datumRodenja;
    }

    public void setDatumRodenja(LocalDate datumRodenja) {
        this.datumRodenja = datumRodenja;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return Objects.equals(id, student.id) && Objects.equals(ime, student.ime) && Objects.equals(prezime, student.prezime) && Objects.equals(fakultetId, student.fakultetId) && Objects.equals(pozicija, student.pozicija) && Objects.equals(datumRodenja, student.datumRodenja);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ime, prezime, fakultetId, pozicija, datumRodenja);
    }
}
