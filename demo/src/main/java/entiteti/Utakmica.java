package entiteti;

import java.util.Objects;

public class Utakmica {
    private Integer id;
    private Integer faks1Id;
    private Integer faks2Id;

    public Utakmica(Integer id, Integer faks1Id, Integer faks2Id) {
        this.id = id;
        this.faks1Id = faks1Id;
        this.faks2Id = faks2Id;
    }

    public Utakmica(Integer faks1Id, Integer faks2Id) {
        this.faks1Id = faks1Id;
        this.faks2Id = faks2Id;
    }

    public Utakmica() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFaks1Id() {
        return faks1Id;
    }

    public void setFaks1Id(Integer faks1Id) {
        this.faks1Id = faks1Id;
    }

    public Integer getFaks2Id() {
        return faks2Id;
    }

    public void setFaks2Id(Integer faks2Id) {
        this.faks2Id = faks2Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utakmica utakmica)) return false;
        return Objects.equals(id, utakmica.id) && Objects.equals(faks1Id, utakmica.faks1Id) && Objects.equals(faks2Id, utakmica.faks2Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, faks1Id, faks2Id);
    }

    @Override
    public String toString() {
        return "Utakmica{" +
                "id=" + id +
                ", faks1Id=" + faks1Id +
                ", faks2Id=" + faks2Id +
                '}';
    }
}
