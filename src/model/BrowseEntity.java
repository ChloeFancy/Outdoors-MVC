package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "browse", schema = "backup", catalog = "")
public class BrowseEntity {
    private int id;
    private Integer idUser;
    private Integer idSpot;
    private Integer count;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_user", nullable = true)
    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Basic
    @Column(name = "id_spot", nullable = true)
    public Integer getIdSpot() {
        return idSpot;
    }

    public void setIdSpot(Integer idSpot) {
        this.idSpot = idSpot;
    }

    @Basic
    @Column(name = "count", nullable = true)
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrowseEntity that = (BrowseEntity) o;
        return id == that.id &&
                Objects.equals(idUser, that.idUser) &&
                Objects.equals(idSpot, that.idSpot) &&
                Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, idUser, idSpot, count);
    }
}
