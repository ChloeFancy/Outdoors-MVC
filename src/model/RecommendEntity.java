package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "recommend", schema = "backup", catalog = "")
public class RecommendEntity {
    private int id;
    private Integer idUser;
    private Integer idSpot;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendEntity that = (RecommendEntity) o;
        return id == that.id &&
                Objects.equals(idUser, that.idUser) &&
                Objects.equals(idSpot, that.idSpot);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, idUser, idSpot);
    }
}
