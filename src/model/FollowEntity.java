package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "follow", schema = "backup", catalog = "")
public class FollowEntity {
    private int id;
    private Integer idFollower;
    private Integer idFollowed;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_follower", nullable = true)
    public Integer getIdFollower() {
        return idFollower;
    }

    public void setIdFollower(Integer idFollower) {
        this.idFollower = idFollower;
    }

    @Basic
    @Column(name = "id_followed", nullable = true)
    public Integer getIdFollowed() {
        return idFollowed;
    }

    public void setIdFollowed(Integer idFollowed) {
        this.idFollowed = idFollowed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowEntity that = (FollowEntity) o;
        return id == that.id &&
                Objects.equals(idFollower, that.idFollower) &&
                Objects.equals(idFollowed, that.idFollowed);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, idFollower, idFollowed);
    }
}
