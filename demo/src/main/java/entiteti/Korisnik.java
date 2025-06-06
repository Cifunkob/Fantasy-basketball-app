package entiteti;

public class Korisnik {
    private Integer id;
    private String username;
    private String password;
    private Integer rolaId;
    private Double bodovi;

    public Korisnik(Integer id, String username, String password, Integer rolaId, Double bodovi) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rolaId = rolaId;
        this.bodovi = bodovi;
    }

    public Korisnik(String username, String password) {
        this.username = username;
        this.password = password;
        this.id=0;
        this.bodovi=0.0;
        this.rolaId=2;
    }

    public Korisnik() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRolaId() {
        return rolaId;
    }

    public void setRolaId(Integer rolaId) {
        this.rolaId = rolaId;
    }

    public Double getBodovi() {
        return bodovi;
    }

    public void setBodovi(Double bodovi) {
        this.bodovi = bodovi;
    }


    @Override
    public String toString() {
        return "Korisnik{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rolaId=" + rolaId +
                ", bodovi=" + bodovi +
                '}';
    }
}
